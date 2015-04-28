
using System;
using System.IO;

namespace MiniClient {
	public class RequestStartProtocol {
		
		public static NetworkRequest Prepare(int roomID) {
			NetworkRequest request = new NetworkRequest(NetworkCode.REQUEST_START);
			request.AddInt32(roomID);
			return request;
		}
		
		public static NetworkResponse Parse(MemoryStream dataStream) {
			RequestStartResponse response = new RequestStartResponse();
			response.status = DataReader.ReadShort(dataStream);
			return response;
		}
	}
	
	public class RequestStartResponse : NetworkResponse {
		public short status { get; set; }

		public RequestStartResponse() {
			protocol_id = NetworkCode.REQUEST_START;
		}
	}
}