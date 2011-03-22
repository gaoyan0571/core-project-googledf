package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Test2 {

	
	public static void test1()
	{
		try {
			
			RandomAccessFile out = new RandomAccessFile("D:\\old\\content.sql", "r");
			
			long start =0;
			long size=102400;
			if(out.length()>size)
			{
				start =out.length()-size;
			}
			else
			{
				size=out.length();
			}
			System.out.println(size);
			System.out.println(start);
			MappedByteBuffer  mb =out.getChannel().map(FileChannel.MapMode.READ_ONLY,start, size);
			
			StringBuffer sb=new StringBuffer();
			int foot=0;
			
			int byteSize =1024;
			byte[] b =new byte[byteSize];
			
			while(mb.hasRemaining())
			{
				
				if(foot>byteSize-1)
				{
					sb.append(new String(b,"utf-8"));
					
					foot =0;
				}
			
				b[foot++]=mb.get();
				
				
			}
			
			if(foot>0)
			{
				byte [] tb=new byte[foot];
				for(int i=0;i<foot;i++)
				{
					tb[i]=b[i];
				}
				sb.append(new String(tb,"utf-8"));
				
			}
			
			
			System.out.println(sb);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	public static String test3(String fileName,long readSize,String encode)
	{
		StringBuffer sb=new StringBuffer();
		try {
			
			RandomAccessFile out = new RandomAccessFile(fileName, "r");
			
			long start =0;
			if(out.length()>readSize)
			{
				start =out.length()-readSize;
			}
			else
			{
				readSize=out.length();
			}
			MappedByteBuffer  mb =out.getChannel().map(FileChannel.MapMode.READ_ONLY,start, readSize);
			
			int foot=0;
			int byteSize =1024;
			byte[] b =new byte[byteSize];
			while(mb.hasRemaining())
			{
				
				if(foot>byteSize-1)
				{
					sb.append(new String(b,encode));
					
					foot =0;
				}
				b[foot++]=mb.get();
			}
			if(foot>0)
			{
				byte [] tb=new byte[foot];
				for(int i=0;i<foot;i++)
				{
					tb[i]=b[i];
				}
				sb.append(new String(tb,encode));
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(" 没有找到文件");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	
	}
	
	
	public static void test2()
	{
		File f =new File("D:\\old\\content.sql");
		try {
			RandomAccessFile rdf =new RandomAccessFile(f, "r");
			
			byte [] b=new byte[1024];
			
			long readSize =1024l;
			long skipStart=0l;
			if(f.length()>1024)
			{
				skipStart =f.length()-readSize;
			}
			rdf.skipBytes((int)skipStart);
			
			for(int i=0;i<b.length;i++)
			{
				b[i]=rdf.readByte();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("---");
		Test2.test1();
	}

}
