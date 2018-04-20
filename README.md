# ListMP3
A Java tool that scan your hard drives for MP3 files and create an HTML report about it.

## Goal

I've started this little project when I began to study computer programming.

The goal was to have an HTML list of the music I had on my computer.

I thought I could enhanced it to be a quick and simple media library.

## Usage

On the command line, you have to run it using JRE with the command `java -cp . listMP3.ListMP3`, which produce this output:

````

ListMP3 v0.36-stable_20060329 by Francois-Xavier MAURICARD

Error : Too less arguments.
Usage : java ListMP3 {-option} [directory] ...

        --all-drives    Process all drives of your computer.

````

**Screenshot of the tool on the CLI:**

![Screenshot of the ListMP3 tool on the CLI](https://github.com/fxmauricard/listmp3/blob/master/ListMP3-screenshot-CLI.png)

**Screenshot of the report made:**

![Screenshot of the report made by ListMP3](https://github.com/fxmauricard/listmp3/blob/master/ListMP3-screenshot-Report.png)

## Conclusion

This project left unfinished, as reading ID3v2 tags was a big deal and I still had to focus on other school projects.
