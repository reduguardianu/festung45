package warGem;

public class Message {
		Integer iD;
		String body;
		
		public Message(int nID,String nBody)
		{
			this.iD=nID;
			this.body=nBody;
		}
		public Integer getiD()
		{
			return iD;
		}
		public String body()
		{
			return body;
		}
}
