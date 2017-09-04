/*
 *  file ListMP3.java
 *  M. MAURICARD Francois-Xavier Pascal - 28/03/2006
 */

import java.io.File;
import java.io.FileWriter;
import java.lang.String;

class ListMP3
{
	final static String VERSION = "v0.35-stable_20060328";
	static boolean even = false;

	public static void main (String[] args)
	{
		System.out.println("\nListMP3 " + VERSION + " by Francois-Xavier MAURICARD\n");

		if (args.length == 0)
		{
			displayHelp();
			return;
		}
		if (args[0].equals("--all-drives"))
		{
			String[] alldrives = { "A:\\", "B:\\", "C:\\", "D:\\", "E:\\", "F:\\", "G:\\", "H:\\", "I:\\", "J:\\", "K:\\", "L:\\", "M:\\", "N:\\", "O:\\", "P:\\", "Q:\\", "R:\\", "S:\\", "T:\\", "U:\\", "V:\\", "W:\\", "X:\\", "Y:\\", "Z:\\" };
			args = alldrives;
		}

		// Deleting the potential existing output file.
		File output = new File("list.htm");
		if (output.exists())
		{
			System.out.println("Deleting existing output file...");
			output.delete();
		}

		writeXHTMLHeader(output);
		writeLine(output, "\t\t<hr />\n");

		DirectoryStatistics ds = new DirectoryStatistics();
		for (String s : args)
		{
			File directory = new File(s);
			boolean error = false;

			// Test if directories passed in arguments exists and can be readed.
			if (!directory.exists())
			{
				System.out.println("Error : " + s + " does not exists.");
				error = true;
			}
			else if (!directory.isDirectory())
			{
				System.out.println("Error : " + s + " is not a directory.");
				error = true;
			}
			else if (!directory.canRead())
			{
				System.out.println("Error : " + s + " cannot be readed.");
				error = true;
			}

			if (!error)
			{
				System.out.println("Processing folder " + directory + "...");
				writeLine(output, "\t\t<h1>" + directory + "</h1>\n\t\t<p>\n");
				ds.add(processDirectory(directory, directory));
			}
		}
		writeLine(output, "\t\t</p>\n\t\t<hr />\n");		
		System.out.println("\n" + ds.file_count + " MP3s found for a total size of " + ds.directory_size / 1048576 + " MB.");
		writeLine(output, "\t\t<h3>" + ds.file_count + " MP3s found for a total size of " + ds.directory_size / 1048576 + " MB.</h3>\n");
		writeXHTMLFooter(output);
	}

	public static DirectoryStatistics processDirectory(File f, File parent)
	{
		DirectoryStatistics res = new DirectoryStatistics();

		if (f.isDirectory())
		{
			// List files of the current directory.
			File[] files = f.listFiles();

			// Test if the list is not null.
			if (files != null && files.length != 0)
			{
				DirectoryStatistics ds = new DirectoryStatistics();
				boolean containsMP3 = false;

				// Search if the current directory contains a MP3.
				for(int i = 0 ; i < files.length && !containsMP3 ; i++)
				{
					String filename = files[i].getName();

					filename = filename.toLowerCase();
					containsMP3 = filename.endsWith(".mp3");
				}
				// If it contains a MP3, we process it.
				if (containsMP3)
				{
					// Managing output file.
					File output = new File("list.htm");

						for (File file : files)
						{
							// Detects if the current file is a cover.
							if (file.getName().equals("Folder.jpg"))
							{
								ds.cover_name = file.getPath();
							}
							ds.add(processDirectory(file, parent));
						}

						// Deletes if necessary the '\' at the beggining of the String defining the child path.
						int root = 1;
						if ((parent.toString().charAt(parent.toString().length() - 1) == '\\'))
						{
							root = 0;
						}
						if (even)
						{
							writeLine(output, "\t\t<div class=\"even\">\n");
							even = false;
						}
						else
						{
							writeLine(output, "\t\t<div class=\"odd\">\n");
							even = true;
						}
						if (ds.cover_name.endsWith("Folder.jpg"))
						{
							writeLine(output, "\t\t\t<img class=\"cover\" src=\"file:///" + ds.cover_name + "\" alt=\"album cover\" height=\"96\" width=\"96\" />\n");
						}
						else
						{
							writeLine(output, "\t\t\t<img class=\"cover\" src=\"folder.png\" alt=\"album cover\" height=\"96\" width=\"96\" />\n");
						}
						writeLine(output, "\t\t\t<div class=\"album\">" + f.toString().substring(parent.toString().length() + root) + "</div>\n");
						writeLine(output, "\t\t\t<div class=\"tracks\">" + ds.file_count + " tracks</div>\n");
						writeLine(output, "\t\t\t<div class=\"infos\">Year ????<br />" + ds.directory_size / 1048576 + " MB</div>\n");
						writeLine(output, "\t\t</div>\n");
						res.add(ds);
				}
				// If it not contains a MP3, we treat it like a directory: recursion.
				else
				{
					for (File file : files)
					{
						res.add(processDirectory(file, parent));
					}
				}
			}
		}

		if (f.isFile())
		{
			String filename = f.getName();

			// Test if the file is a MP3 or not.
			filename = filename.toLowerCase();
			if (filename.endsWith(".mp3"))
			{
				res.file_count++;
				res.directory_size = f.length();
			}
		}

		return res;
	}

	public static void displayHelp()
	{
		System.out.println("Error : Too less arguments.\nUsage : java ListMP3 {-option} [directory] ...\n");
		System.out.println("\t--all-drives\tProcess all drives of your computer.");
	}

	public static void writeXHTMLHeader(File f)
	{
		writeLine(f, "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		writeLine(f, "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n");
		writeLine(f, "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">\n");
		writeLine(f, "\t<head>\n");
		writeLine(f, "\t\t<title>HTML document generated by ListMP3</title>\n");
		writeLine(f, "\t\t<style type=\"text/css\" title=\"Maroon\">\n");
		writeLine(f, "\t\t\th3 { text-align: center ; font-weight: normal ; font-size: medium ; font-family: \"Arial\", Helvetica, sans-serif ; color: black }\n");
		writeLine(f, "\t\t\th6 { text-align: center ; font-weight: bold ; font-size: xx-small ; font-family: \"Arial\", Helvetica, sans-serif ; color: maroon }\n");
		writeLine(f, "\t\t\t.folder { font-weight: bold ; font-size: x-large ; font-family: \"Arial\", Helvetica, sans-serif ; color: black ; background-color : #A5A251 }\n");
		writeLine(f, "\t\t\t.cover { float: left }\n");
		writeLine(f, "\t\t\t.odd { background-color: #A58161 ; height: 96px }\n");
		writeLine(f, "\t\t\t.odd:hover { background-color: #A5A251 }\n");
		writeLine(f, "\t\t\t.even { background-color: #BCA289 ; height: 96px }\n");
		writeLine(f, "\t\t\t.even:hover { background-color: #A5A251 }\n");
		writeLine(f, "\t\t\t.album { font-weight: bold; font-size: large ; font-family: \"Century Gothic\", Helvetica, sans-serif ; line-height: 1.0em ; color: white ; position: relative ; top: 0.5em ; left: 4px }\n");
		writeLine(f, "\t\t\t.tracks { font-weight: normal ; font-size: medium ; font-family: \"Century Gothic\", Helvetica, sans-serif ; line-height: 1.0em ; color: white ; position: relative ; top: 1em ; left: 4px }\n");
		writeLine(f, "\t\t\t.infos { text-align: right ; font-weight: normal ; font-size: small ; font-family: \"Century Gothic\", Helvetica, sans-serif ; line-height: 1.0em ; color: white ; position: relative ; top: -2em ; right: 5px }\n");
		writeLine(f, "\t\t</style>\n");
		writeLine(f, "\t\t<style type=\"text/css\" title=\"Blue\">\n");
		writeLine(f, "\t\t\th3 { text-align: center ; font-weight: normal ; font-size: medium ; font-family: \"Arial\", Helvetica, sans-serif ; color: black }\n");
		writeLine(f, "\t\t\th6 { text-align: center ; font-weight: bold ; font-size: xx-small ; font-family: \"Arial\", Helvetica, sans-serif ; color: maroon }\n");
		writeLine(f, "\t\t\t.folder { font-weight: bold ; font-size: x-large ; font-family: \"Arial\", Helvetica, sans-serif ; color: black ; background-color : #4F889B }\n");
		writeLine(f, "\t\t\t.cover { float: left }\n");
		writeLine(f, "\t\t\t.odd { background-color: #8BB7C5 ; height: 96px }\n");
		writeLine(f, "\t\t\t.odd:hover { background-color: #4F889B }\n");
		writeLine(f, "\t\t\t.even { background-color: #BED7DE ; height: 96px }\n");
		writeLine(f, "\t\t\t.even:hover { background-color: #4F889B }\n");
		writeLine(f, "\t\t\t.album { font-weight: bold; font-size: large ; font-family: \"Century Gothic\", Helvetica, sans-serif ; line-height: 1.0em ; color: white ; position: relative ; top: 0.5em ; left: 4px }\n");
		writeLine(f, "\t\t\t.tracks { font-weight: normal ; font-size: medium ; font-family: \"Century Gothic\", Helvetica, sans-serif ; line-height: 1.0em ; color: white ; position: relative ; top: 1em ; left: 4px }\n");
		writeLine(f, "\t\t\t.infos { text-align: right ; font-weight: normal ; font-size: small ; font-family: \"Century Gothic\", Helvetica, sans-serif ; line-height: 1.0em ; color: white ; position: relative ; top: -2em ; right: 5px }\n");
		writeLine(f, "\t\t</style>\n");
		writeLine(f, "\t</head>\n");
		writeLine(f, "\t<body>\n");
	}

	public static void writeLine(File f, String s)
	{
		try
		{
			FileWriter fw = new FileWriter(f, true);
			fw.write(s);
			fw.close();
		}
		catch (java.io.IOException e)
		{
			System.out.println("Error : Could not create/write output file.");
		}
	}

	public static void writeXHTMLFooter(File f)
	{
		writeLine(f, "\t\t<h6>Generated by ListMP3 " + VERSION + " created by Francois-Xavier MAURICARD</h6>\n");
		writeLine(f, "\t</body>\n");
		writeLine(f, "</html>\n");
	}
}
