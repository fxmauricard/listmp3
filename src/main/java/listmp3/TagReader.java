package listmp3;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.String;

/**
 * The {@code PlaylistPTagReaderarser} class is a utility class to parse multiple tag format.
 *
 * @author	Francois-Xavier MAURICARD
 */
public final class TagReader extends Object
{
	// Constants.
	public final static String[] ID3GENRE =	{
		"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", 
		"Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B",
		"Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska",
		"Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", 
		"Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical",
		"Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise",
		"AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative",
		"Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave",
		"Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream",
		"Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap",
		"Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic",
		"Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz",
		"Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock",
		"National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic",
		"Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock",
		"Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", 
		"Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata",
		"Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club",
		"Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul",
		"Freestyle", "Duet", "Punk Rock", "Drum Solo", "Acapella", "Euro-House", "Dance Hall",
		"Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", 
		"Negerpunk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal",
		"Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue",
		"Salsa", "Thrash Metal", "Anime", "JPop", "Synthpop"
	};

	// Prevent instanciation of the class.
	private TagReader()
	{
	}

	// Class methods.
	public static Track readID3v1(File track)
	{
		Track trk = new Track();

		try
		{
			// Opening file in read only mode.
			RandomAccessFile raf = new RandomAccessFile(track, "r");
			// Creating read buffer.
			byte[] id3_buffer = new byte[128];
			// Reading ID3v1 tag.
			raf.seek(track.length() - 128);
			if (raf.read(id3_buffer) != -1)
			{
				// Converts buffer in String.
				String id3 = new String(id3_buffer);
				// Test if it's an ID3v1 tag.
				if (id3.startsWith("TAG"))
				{
					trk.title = id3.substring(3, 33);
					trk.artist = id3.substring(33, 63);
					trk.album = id3.substring(63, 93);
					try
					{
						trk.year = Integer.parseInt(id3.substring(93, 97));
					}
					catch (java.lang.NumberFormatException e)
					{
					}
					// Checks if it's an ID3v1.1 tag, if yes, reading the number of the list.
					if (id3.charAt(125) == 0)
					{
						trk.comment = id3.substring(97, 125);
						trk.album_track = id3_buffer[126];
					}
					else
					{
						trk.comment = id3.substring(97, 127);
					}
					// Test if genre number is in the array. !!! -1 seems to be undefined !!!
					if (id3_buffer[127] >= 0 && id3_buffer[127] < ID3GENRE.length - 1)
					{
						trk.genre = ID3GENRE[id3_buffer[127]];
					}
					trk.size = track.length();
				}
				else
				{
					// DEBUG ONLY
					trk.size = track.length();
					// DEBUG ONLY
				}
			}
			raf.close();
		}
		catch (java.io.IOException e)
		{
			System.out.println("Error : File could not be found/readed.");
		}

		return trk;
	}
}
