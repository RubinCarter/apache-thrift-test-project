#
# Autogenerated by Thrift Compiler (0.9.3)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py:new_style
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException

from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None


class RequestType(object):
  SAY_HELLO = 0
  QUERY_TIME = 1

  _VALUES_TO_NAMES = {
    0: "SAY_HELLO",
    1: "QUERY_TIME",
  }

  _NAMES_TO_VALUES = {
    "SAY_HELLO": 0,
    "QUERY_TIME": 1,
  }
