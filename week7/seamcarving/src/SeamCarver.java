import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.util.Stack;
public class SeamCarver {
    private Picture myPicture;
    private double[][] Energy;
    public SeamCarver(Picture picture)
    {
        if(picture == null)
            throw new java.lang.IllegalArgumentException();
        myPicture = new Picture(picture);
        Energy = new double[width()][height()];
        for(int col=0; col<width();col++)
            for(int row=0;row<height();row++)
            {
                Energy[col][row] = energy(col,row);
            }
    }// create a seam carver object based on the given picture
    public Picture picture()
    {
        return new Picture(myPicture);
    }// current picture
    public     int width()
    {
        return myPicture.width();
    }// width of current picture
    public  int height()
    {
        return myPicture.height();
    }// height of current picture
    public  double energy(int x, int y)
    {
        if(x<0 || x>width()-1 || y<0 || y>height()-1)
            throw new java.lang.IllegalArgumentException();
        if(x == 0 || x == width() -1 || y==0 || y == height()-1)
            return 1000.0;
        Color Up = myPicture.get(x, y-1);
        Color Down = myPicture.get(x, y+1);
        Color Left = myPicture.get(x-1, y);
        Color Right = myPicture.get(x+1, y);
        double deltaxsquare = Math.pow(Left.getBlue()-Right.getBlue(), 2)+Math.pow(Left.getGreen()-Right.getGreen(), 2)+Math.pow(Left.getRed()-Right.getRed(), 2);
        double deltaysquare = Math.pow(Up.getBlue()-Down.getBlue(), 2)+Math.pow(Up.getGreen()-Down.getGreen(), 2)+Math.pow(Up.getRed()-Down.getRed(), 2);
        return Math.sqrt(deltaxsquare+deltaysquare);
    }// energy of pixel at column x and row y
    private double getUL(int i,int j,double[][] Vertical)
    {
        if(i-1 <0 || j -1 <0 )
            return Double.MAX_VALUE;
        else
            return Vertical[i - 1][j - 1];
    }
    private double getUP(int i,int j,double[][] Vertical)
    {
        if(j - 1 < 0)
            return Double.MAX_VALUE;
        else
            return Vertical[i][j-1];
    }
    private double getUR(int i,int j,double[][] Vertical)
    {
        if(i+1>=width()||j-1 <0)
            return Double.MAX_VALUE;
        else
            return Vertical[i+1][j-1];
    }
    private int getRoute(int i, int j,double[][] Vertical)//return the num of previous pixel in the previous row
    {
        double UL = getUL(i,j,Vertical);
        double UP = getUP(i,j,Vertical);
        double UR = getUR(i,j,Vertical);
        int answer = 0;
        double tempEnergy = 0.0;
        if(UL <= UP)
        {
            answer = i - 1 ;
            tempEnergy = UL;
        }
        else
        {
            answer = i;
            tempEnergy = UP;
        }
        if(UR < tempEnergy)
        {
            answer = i +1;
            tempEnergy = UR;
        }
        return answer;
    }
    
    private void PictureTranspose()
    {
        if(myPicture == null)
            throw new java.lang.IllegalArgumentException();
        Picture newPicture = new Picture(height(), width());
        for(int row = 0; row < height(); row ++)
            for(int col = 0 ; col < width(); col++)
            {
                int PixelColor = myPicture.getRGB(col, row);
                newPicture.setRGB(row, col, PixelColor);
            }//Transpose Picture
        this.myPicture = newPicture;
        double[][] newEnergy = new double[Energy[0].length][Energy.length];
        for(int col=0; col < width(); col++)
            for(int row=0; row < height(); row++)
            {
                newEnergy[col][row] = Energy[row][col];
            }//Transpose Energy
        Energy = newEnergy;
        
    }
    public  int[] findHorizontalSeam()
    {
        int[] HorizontalSeam = new int[width()];
        PictureTranspose();
        HorizontalSeam = findVerticalSeam();
        PictureTranspose();
        return HorizontalSeam;
    }// sequence of indices for horizontal seam
    
    public  int[] findVerticalSeam()
    {
        int[][] route = new int[width()][height()];
        double[][] Vertical = new double[width()][height()];
        for(int row = 0 ; row<height() ; row++)
            for(int col=0 ; col<width() ; col++)
            {               
                route[col][row] = getRoute(col,row,Vertical);
                if(row -1 < 0)
                    Vertical[col][row] = Energy[col][row];
                else
                    Vertical[col][row] = Vertical[route[col][row]][row-1]+Energy[col][row];
            }
        double minEnergy = Double.MAX_VALUE;
        int loc = 0;//get the location of min energy sum of the last row
        for(int col = 0 ;col<width();col++)
        {
            if(Vertical[col][height()-1]<minEnergy)
            {
                loc = col;
                minEnergy = Vertical[col][height()-1];
            }
        }
        Stack<Integer> st = new Stack<Integer>();
        st.push(loc);
        for(int row = height() - 1 ;row > 0; row--)
        {
            loc = route[loc][row];
            st.push(loc);
        }
        int[] VerticalRoute = new int[height()];
        int num = 0;
        while(!st.isEmpty())
        {
            VerticalRoute[num] = st.pop();
            num++;
        }
        return VerticalRoute;
    }// sequence of indices for vertical seam
    public  void removeHorizontalSeam(int[] seam)
    {
        PictureTranspose();
        removeVerticalSeam(seam);
        PictureTranspose();
    }// remove horizontal seam from current picture
    public void removeVerticalSeam(int[] seam)
    {
        if(seam == null || seam.length != height() || myPicture.width() <= 1)
            throw new java.lang.IllegalArgumentException();
        for(int i=0 ; i<seam.length; i++)
        {
        	 	if( seam[i] >= myPicture.width() || seam[i] < 0)
                throw new java.lang.IllegalArgumentException();
            if(i >= 1 && Math.abs(seam[i]-seam[i-1]) > 1)
                throw new java.lang.IllegalArgumentException();
        }
        Picture newPicture = new Picture(width() - 1, height());
        for(int row = 0; row < height(); row++)
        {
            for(int col = 0; col<width()-1; col++)
            {
                if(col < seam[row])
                {
                    int PixelColor = myPicture.getRGB(col, row);
                    newPicture.setRGB(col, row, PixelColor);
                }
                else
                {
                    int PixelColor = myPicture.getRGB(col + 1, row);
                    newPicture.setRGB(col, row, PixelColor);
                }
            }
        }
        this.myPicture = newPicture;
        for(int row = 0;row < height();row++)
            for(int col = seam[row] ;col<width();col++)
            {
                //if(col + 1 < Energy.length)
                Energy[col][row] = Energy[col+1][row];//plug the hole
            }
        for(int row = 0;row<height();row++)
        {
            if(seam[row]<width())
                Energy[seam[row]][row]=energy(seam[row],row);
            if(seam[row]+1<width())
                Energy[seam[row]+1][row]=energy(seam[row]+1,row);
            if(seam[row]-1>=0)
                Energy[seam[row]-1][row]=energy(seam[row]-1,row);
            if(row+1 < height()&&seam[row]<width())
                Energy[seam[row]][row+1]=energy(seam[row],row+1);
            if(row-1 >= 0&&seam[row]<width())
                Energy[seam[row]][row-1]=energy(seam[row],row-1);
        }//update Energy
    }// remove vertical seam from current picture
    
    public static void main(String[] args) {
        Picture inputImg = new Picture("chameleon.png");
        int removeColumns = 100;
        int removeRows = 100;
        
        StdOut.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        SeamCarver sc = new SeamCarver(inputImg);
        Stopwatch sw = new Stopwatch();
        
        for (int i = 0; i < removeRows; i++) {
            int[] horizontalSeam = sc.findHorizontalSeam();
            sc.removeHorizontalSeam(horizontalSeam);
        }
        
        for (int i = 0; i < removeColumns; i++) {
            int[] verticalSeam = sc.findVerticalSeam();
            sc.removeVerticalSeam(verticalSeam);
        }
        Picture outputImg = sc.picture();
        StdOut.printf("new image size is %d columns by %d rows\n", sc.width(), sc.height());
        StdOut.println("Resizing time: " + sw.elapsedTime() + " seconds.");
        inputImg.show();
        outputImg.show();
    }
    
}