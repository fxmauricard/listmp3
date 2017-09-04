/*
 *  file DirectoryStatistics.java
 *  M. MAURICARD Francois-Xavier Pascal - 16/03/2006
 */
 
 public class DirectoryStatistics extends Object
{
	// Attributes.
	public int file_count;
	public long directory_size;
	
	// Constructors.
	public DirectoryStatistics()
	{
		this.file_count = 0;
		this.directory_size = 0;
	}
	
	// Instance methods.
	public void add(DirectoryStatistics ds)
	{
		this.file_count += ds.file_count;
		this.directory_size += ds.directory_size;
	}
}
