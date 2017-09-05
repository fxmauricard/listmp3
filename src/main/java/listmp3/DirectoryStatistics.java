/*
 *  file DirectoryStatistics.java
 *  M. MAURICARD Francois-Xavier Pascal - 29/03/2006
 */

package listmp3;

public class DirectoryStatistics extends Object
{
	// Attributes.
	public int file_count;
	public long directory_size;
	public String cover_name;
	
	// Constructors.
	public DirectoryStatistics()
	{
		this.file_count = 0;
		this.directory_size = 0;
		this.cover_name = "";
	}
	
	// Instance methods.
	public void add(DirectoryStatistics ds)
	{
		this.file_count += ds.file_count;
		this.directory_size += ds.directory_size;
		if (this.cover_name == "")
		{
			this.cover_name = ds.cover_name;
		}
	}
}
