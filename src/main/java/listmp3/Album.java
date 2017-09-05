package listmp3;

/**
 * The {@code Album} class defines a music album.
 *
 * @author	Francois-Xavier MAURICARD
 */
public class Album extends Object
{
	// Attributes.
	public int tracks_number;
	public long folder_size;
	public String cover_path;

	// Constructors.
	public Album()
	{
		this.tracks_number = 0;
		this.folder_size = 0;
		this.cover_path = "";
	}

	// Instance methods.
	public void fusion(Album album2)
	{
		this.tracks_number += album2.tracks_number;
		this.folder_size += album2.folder_size;
		if (this.cover_path == "")
		{
			this.cover_path = album2.cover_path;
		}
	}
}
