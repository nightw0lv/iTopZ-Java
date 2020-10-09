import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;

/**
 * 
 * @author nightwolf
 */
public class Main
{
	private int votes;
	private int rank;
	private int wanted_votes;
	private int next_rank;
	private String iTopZ = "https://itopz.com/check/";
	private String ApiKey;
	private int ServerId;
	
	/**
	 * main function to call
	 * prints server data 
	 */
	public void main()
	{
		ApiKey = "d4IUPIZVgS-rkTvbL3fVGLEB5zhFdKq"; // Config.ITOPZ_API_KEY (for debug you can use 'DEMO')
		ServerId = 325339; // Config.ITOPZ_SERVER_ID (for debug you can use and id (int))
		
		getVotes();
		System.out.println("Server Votes: " + votes);
		System.out.println("Server Ranking: " + rank);
		System.out.println("Server next rank votes needed: " + wanted_votes);
		System.out.println("Server next rank will be: " + next_rank);
	}
	
	/**
	 * get votes
	 */
	private int getVotes()
	{
		String iTopZurl = iTopZ + ApiKey + "/" + ServerId + "/";
		URLConnection con = null;
		try
		{
			con = new URL(iTopZurl).openConnection();
			con.addRequestProperty("User-Agent", "Mozilla/4.76");
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())))
			{
				String line;
				while ((line = br.readLine()) != null)
				{
					if (line.contains("server_votes"))
					{
						votes = Integer.valueOf(line.replaceAll("[^\\d]", ""));
					}
					if (line.contains("server_rank"))
					{
						rank = Integer.valueOf(line.replaceAll("[^\\d]", ""));
					}
					if (line.contains("next_rank_votes"))
					{
						wanted_votes = Integer.valueOf(line.replaceAll("[^\\d]", ""));
					}
					if (line.contains("next_rank"))
					{
						next_rank = Integer.valueOf(line.replaceAll("[^\\d]", ""));
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Error while getting iTopZ server info.");
		}
		return -1;
	}
}
