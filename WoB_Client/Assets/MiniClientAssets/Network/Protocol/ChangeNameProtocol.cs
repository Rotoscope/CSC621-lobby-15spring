using System;
using System.IO;

namespace MiniClient {
public class ChangeNameProtocol {

	public static NetworkRequest Prepare(string name) {
		NetworkRequest request = new NetworkRequest(NetworkCode.CHANGE_NAME);
		request.AddString(name);
		return request;
	}
	
	public static NetworkResponse Parse(MemoryStream dataStream) {
		return null;
	}
}
}