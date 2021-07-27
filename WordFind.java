/**
@author Issifu Alhassan
@version 1.0.0
*/
import java.util.ArrayList;
public class WordFind
{
	public static void main(String [] args)
	{
		char[][] puzzle = {
		{'b','o','y','i','y'},
		{'r','o','m','u','r'},
		{'a','m','y','b','k'},
		{'g','r','a','b','c'},
		{'p','r','g','n','u'},
		{'c','u','a','u','b'},
		{'o','t','u','n','e'}};

		String[] words = {"boy","brag","cobra","comb","argue","tune","you","grab","crab","rum","ran","rang","pry","moo","nun","too","cube","buck","neo","nut","toe","gay","otu","man","bar"};
		ArrayList<String> foundWords = findWords(puzzle,words);
		System.out.println(foundWords);
		System.out.println(foundWords.size() + " found and " + (words.length-foundWords.size())+" not found");
	}

	/**
	*findWords method finds and returns the list of words in a puzzle
	*@param puzzle : the characters in the puzzle in 2D array
	*@param words : an array of the words to be found
	*/
	public static ArrayList<String> findWords(char[][] puzzle, String[] words)
	{
		ArrayList<String> foundWords = new ArrayList<>(); 
		int row = puzzle.length;
		if(row==0)
		{
			return foundWords;
		}
		int wordLength,i,j;
		char fc,c;
		int col = puzzle[0].length;
		boolean found;
		for(String word:words)
		{
			
			found = false;
			wordLength = word.length();
			if(wordLength<1)///minimum word length
			{
				continue;
			}
			fc = word.charAt(0);
			for(i=0;i<row;i++)
			{
				if(found){break;}
				for(j=0;j<col;j++)
				{
					if(found){break;}
					if(puzzle[i][j]==fc)//first character found
					{
						String track = word + ": ["+i+","+j+"]";
						//check east with text wrapping
						int ci = 1;
						for(int k = j+1; k<wordLength+j; k++)
						{
							if(found){break;}//redundant
							if(puzzle[i][k%col]==word.charAt(ci))
							{
								track+= "["+i+","+(k%col)+"]";
								ci++;
								if(ci==wordLength)
								{
									foundWords.add(word);
									found = true;
									System.out.println(track);
								}
							}
							else
							{
								track = word + ": " +  "["+i+","+j+"]";
								break;
							}
						}

						//check west with text wrapping
						ci = 1;
						for(int k = j-1; k>j-wordLength; k--)
						{
							if(found){break;}//redundant
							if(puzzle[i][(k+col)%col]==word.charAt(ci))
							{
								track+= "["+i+","+((k+col)%col)+"]";
								ci++;
								if(ci==wordLength)
								{
									foundWords.add(word);
									found = true;
									System.out.println(track);
								}
							}
							else
							{
								track = word + ": "+ "["+i+","+j+"]";
								break;
							}
						}

						//check south with text wrapping
						ci = 1;
						for(int k = i+1; k<wordLength+i; k++)
						{
							if(found){break;}//redundant
							if(puzzle[k%row][j]==word.charAt(ci))
							{
								track+= "["+(k%row)+","+j+"]";
								ci++;
								if(ci==wordLength)
								{
									foundWords.add(word);
									found = true;
									System.out.println(track);
								}
							}
							else
							{
								track = word + ": " +  "["+i+","+j+"]";
								break;
							}
						}

						//check north with text wrapping
						ci = 1;
						for(int k = i-1; k>i-wordLength; k--)
						{
							if(found){break;}//redundant
							if(puzzle[(k+row)%row][j]==word.charAt(ci))
							{
								track+= "["+((k+row)%row)+","+j+"]";
								ci++;
								if(ci==wordLength)
								{
									foundWords.add(word);
									found = true;
									System.out.println(track);
								}
							}
							else
							{
								track = word + ": " +  "["+i+","+j+"]";
								break;
							}
						}

						//check southeast with text wrapping
						int diagLength_ne = 0;
						int diagLength_se = 0;
						int i_end_ne;
						int j_end_ne;
						int i_end_nw;
						int j_end_nw;
						int i_end_se;
						int j_end_se;
						int i_end_sw;
						int j_end_sw;

						int m = i;
						int n = j;
						//go north east to find length
						while(m!=0 && n!=col-1)
						{
							diagLength_ne++;
							m--;
							n++;
						}
						i_end_ne = m;
						j_end_ne = n;


						//go south west to find length
						m = i;
						n = j;
						while(m!=row-1 && n!=0)
						{
							diagLength_ne++;
							m++;
							n--;
						}
						i_end_sw = m;
						j_end_sw = n;


						//go south east to find length
						m = i;
						n = j;
						while(m!=row-1 && n!=col-1)
						{
							diagLength_se++;
							m++;
							n++;
						}
						i_end_se = m;
						j_end_se = n;
						


						//go north west to find length
						m = i;
						n = j;
						while(m!=0 && n!=0)
						{
							diagLength_se++;
							m--;
							n--;
						}
						i_end_nw = m;
						j_end_nw = n;

						//System.out.println(i+","+j+": diagLength_ne = "+diagLength_ne+"   diagLength_se = "+diagLength_se);

						//check northeast with text wrapping
						m=i;
						n=j;
						if(!found && wordLength-1 <= diagLength_ne)
						{
							found = true;
							for(int z = 1;z<wordLength;z++)
							{
								if(m==i_end_ne || n == j_end_ne)
								{
									m = i_end_sw;
									n = j_end_sw;
								}
								else
								{
									m--;
									n++;
								}
								if(puzzle[m][n]!=word.charAt(z))
								{
									track = word + ": " +  "["+i+","+j+"]";
									found = false;
									break;
								}
								track+= "["+m+","+n+"]";
							}
							if(found)
							{
								foundWords.add(word);
								System.out.println(track);
							}
								
						}


						//check northwest with text wrapping
						m=i;
						n=j;
						if(!found && wordLength-1 <= diagLength_se)
						{
							for(int z = 1;z<wordLength;z++)
							{
								if(m==i_end_nw || n == j_end_nw)
								{
									m = i_end_se;
									n = j_end_se;
								}
								else
								{
									m--;
									n--;
								}
								if(puzzle[m][n]!=word.charAt(z))
								{
									track = word + ": " +  "["+i+","+j+"]";
									found = false;
									break;
								}
								track+= "["+m+","+n+"]";
							}
							if(found)
							{
								foundWords.add(word);
								System.out.println(track);
							}
						}

						//check southeast with text wrapping
						m=i;
						n=j;
						if(!found && wordLength-1 <= diagLength_se)
						{
							for(int z = 1;z<wordLength;z++)
							{
								if(m==i_end_se || n == j_end_se)
								{
									m = i_end_nw;
									n = j_end_nw;
								}
								else
								{
									m++;
									n++;
								}
								if(puzzle[m][n]!=word.charAt(z))
								{
									track = word + ": " +  "["+i+","+j+"]";
									found = false;
									break;
								}
								track+= "["+m+","+n+"]";
							}
							if(found)
							{
								foundWords.add(word);
								System.out.println(track);
							}
						}
						
						//check southwest with text wrapping
						m=i;
						n=j;
						if(!found && wordLength-1 <= diagLength_ne)
						{
							for(int z = 1;z<wordLength;z++)
							{
								if(m==i_end_sw || n == j_end_sw)
								{
									m = i_end_ne;
									n = j_end_ne;
								}
								else
								{
									m++;
									n--;
								}
								if(puzzle[m][n]!=word.charAt(z))
								{
									track = word + ": " +  "["+i+","+j+"]";
									found = false;
									break;
								}
								track+= "["+m+","+n+"]";
							}
							if(found)
							{
								foundWords.add(word);
								System.out.println(track);
							}
						}
					}
				}
			}
		}
		return foundWords;	
	}
}