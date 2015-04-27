using System;
using System.IO;

public class PairProtocol {
	
	public static NetworkRequest Prepare() {
		NetworkRequest request = new NetworkRequest(NetworkCode.PAIR);
		return request;
	}
	
	public static NetworkResponse Parse(MemoryStream dataStream) {
		var response = new ResponsePair();
		response.status = DataReader.ReadShort(dataStream);
		return response;
	}
}

public class ResponsePair : NetworkResponse {
	
	public short status { get; set; }

	public ResponsePair() {
		protocol_id = NetworkCode.PAIR;
	}
}
