import asyncio
from aiohttp import web
import json
from japy.japy_file_scanner import JapyFileScanner
import threading


class JapyServer:

    def __init__(self, japy_file_scanner: JapyFileScanner):
        self.methods = dict(japy_file_scanner.scan_for_decorated_methods())
        self.app = web.Application()
        self.define_routes()
        self.server_runs = False
        self.server_started_event = threading.Event()
        self.loop = None

    async def start_server(self):
        if self.server_runs:
            return
        self.runner = web.AppRunner(self.app)
        await self.runner.setup()
        site = web.TCPSite(self.runner, '127.0.0.1', 12345)
        await site.start()
        self.server_runs = True
        self.server_started_event.set()

    async def stop_server(self):
        if self.server_runs:
            self.server_runs = False
            await self.runner.cleanup()  # Säubert den Server
            self.loop.stop()  # Beende den Event-Loop

    async def handle_call_method(self, request):
        try:
            data = await request.json()

            if not data:
                return web.json_response({'error': 'No Data send'}, status=400)

            if data['name'] not in self.methods:
                return web.json_response(
                    {'error': f'Method {data["name"]} could not be found', 'existingMethods': list(self.methods.keys())},
                    status=404)

            func = self.methods[data['name']]
            result = func.call_method(data['arguments'])

            return web.json_response(result.to_dict(), status=200)

        except KeyError as e:
            return web.json_response({'error': f'Missing key: {str(e)}'}, status=400)

        except json.JSONDecodeError as e:
            return web.json_response({'error': 'Invalid JSON format'}, status=400)

        except Exception as e:
            return web.json_response({'error': str(e)}, status=500)

    def define_routes(self):
        self.app.router.add_post('/japy/call_method', self.handle_call_method)


japy_server_manager = None


class JapyServerManagerAlreadyExistsException(Exception):

    def __init__(self):
        super().__init__("JapyServerManager already exists")


class JapyServerManager:

    def __init__(self, japy_file_scanner: JapyFileScanner):
        if japy_server_manager is not None:
            raise JapyServerManagerAlreadyExistsException()
        self.server = None
        self.loop = None
        self.server_thread = None
        self.japy_file_scanner = japy_file_scanner

    def start_japy_server(self):
        self.server = JapyServer(self.japy_file_scanner)

        def run_async_server(loop):
            self.server.loop = loop
            asyncio.set_event_loop(loop)
            loop.run_until_complete(self.server.start_server())
            loop.run_forever()

        self.loop = asyncio.new_event_loop()

        self.server_thread = threading.Thread(target=run_async_server, args=(self.loop,))
        self.server_thread.start()

        self.server.server_started_event.wait()

    def stop_japy_server(self):
        asyncio.run_coroutine_threadsafe(self.server.stop_server(), self.loop)

        self.server_thread.join()


def get_japy_server_manager(japy_file_scanner: JapyFileScanner) -> JapyServerManager:
    global japy_server_manager
    if japy_server_manager is None:
        japy_server_manager = JapyServerManager(japy_file_scanner)

    return japy_server_manager

