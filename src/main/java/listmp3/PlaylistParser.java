package listmp3;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.String;
import java.util.ArrayList;

/**
 * The {@code PlaylistParser} class is a utility class to parse multiple playlist format.
 *
 * @author	Francois-Xavier MAURICARD
 */
public final class PlaylistParser extends Object
{
	// Prevent instanciation of the class.
	private PlaylistParser()
	{
	}
	
	public static File[] parseM3U(File playlist)
	{
		ArrayList<File> al = new ArrayList<File>();

		try
		{
			// Opening file in read only mode.
			RandomAccessFile raf = new RandomAccessFile(playlist, "r");
			// Creating read buffer.
			String line;
			// Reading file, line by line.
			while ((line = raf.readLine()) != null )
			{
				// Parsing M3U, removing comments, starting by #.
				if (line.charAt(0) != '#')
				{
					if (line.length() > 3 && line.charAt(1) == ':' && line.charAt(2) == '\\')
					{
						al.add(new File(line));
					}
					else
					{
						al.add(new File(playlist.getParent() + "\\" + line));
					}
				}
			}
			raf.close();
		}
		catch (java.io.IOException e)
		{
			System.out.println("Error : M3U playlist could not be found/readed.");
		}

		return al.toArray(new File[al.size()]);
	}

	public static Album parseExtendedM3U(File playlist)
	{
		Album album = new Album();

		try
		{
			// Opening playlist file, in read only mode.
			RandomAccessFile raf = new RandomAccessFile(playlist, "r");
			// Creating read buffer.
			String line;
			// Test if the playlist is an Extended M3U or a M3U.
			if (raf.readLine() == "#EXTM3U")
			{
			}
			else
			{

			}
			raf.close();
		}
		catch (java.io.IOException e)
		{
			System.out.println("Error : M3U playlist could not be found/readed.");
		}

		return album;
	}
}
