package listmp3;

import java.lang.String;

/**
 * The {@code Track} class defines a music track.
 *
 * @author	Francois-Xavier MAURICARD
 */
public class Track extends Object
{
	// Attributes.
	public String title;
	public String artist;
	public String album;
	public int year;
	public String comment;
	public int album_track;
	public String genre;
	public long size;

	// Constructors.
	public Track()
	{
		this.title = "";
		this.artist = "";
		this.album = "";
		this.year = 0;
		this.comment = "";
		this.album_track = 0;
		this.genre = "";
		this.size = 0;
	}

	// Instance methods.
	public String toString()
	{
		return "\nTitle : " + this.title + "\nArtist : " + this.artist + "\nAlbum : " + this.album + "\nYear : " + this.year + "\nComment : " + this.comment + "\nAlbum track : " + this.album_track + "\nGenre : " + this.genre + "\nSize : " + this.size;
	}
}
