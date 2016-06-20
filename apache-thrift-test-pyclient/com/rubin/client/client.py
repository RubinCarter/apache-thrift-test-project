from com.rubin.service import HelloServcie

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol.TBinaryProtocol import TBinaryProtocol
from thrift.protocol.TMultiplexedProtocol import TMultiplexedProtocol

try:
    transport = TSocket.TSocket('localhost', 7910)
    transport = TTransport.TBufferedTransport(transport)
    protocol = TBinaryProtocol(transport)
    client = HelloServcie.Client(TMultiplexedProtocol(protocol, 'helloService'))

    transport.open()

    result = client.helloString("HelloWorld")
    print(result)
    transport.close()
except Thrift.TException as tx:
    print('%s' % tx.message)
