package map.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class PushbackReader extends BufferedReader {
    private  int     pushBack = ' ';
    private  boolean isPushBack = false;

    public PushbackReader(Reader in)
    {
        super( in );
    }

    public int read() throws IOException
    {
        int chr;

        if ( isPushBack )
        {
            isPushBack = false;
            chr = pushBack;
            return chr;
        }

        chr = super.read();

        return chr;
    }

    //  "return" a char to the input stream.
    public void unread( char c ) throws IOException
    {
        if ( isPushBack ) throw new IOException("Pushback buffer is full");
        pushBack   = (int)c;
        isPushBack = true;
    }

}
