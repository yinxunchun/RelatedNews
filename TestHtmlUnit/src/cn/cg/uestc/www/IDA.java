package cn.cg.uestc.www;

public class IDA {  
	  
    //�ֱ�������ϡ��ҡ����ĸ��ƶ�����Ĳ�����  
    private int[] up = {-1,0};  
    private int[] down = {1,0};  
    private int[] left = {0,-1};  
    private int[] right = {0,1};  
      
    /**ע�⣬����UP��DOWN��LEFT��RIGHT������������Եģ���Ϊ���������ʹ�� 
     * ((dPrev != dCurr) && (dPrev%2 == dCurr%2)) 
     * ���ж�ǰ�������ƶ������Ƿ��෴ 
     */  
    private final int UP = 0;  
    private final int DOWN = 2;  
    private final int LEFT = 1;  
    private final int RIGHT = 3;  
      
    private int SIZE;  
      
    //����Ŀ�������  
    private int[][] targetPoints;   
      
    //���ڼ�¼�ƶ����裬�洢0,1,2,3,��Ӧ�ϣ��£�����  
    private static int[] moves = new int[100000];  
      
    private static long ans = 0;; //��ǰ������"�������"  
      
    //Ŀ��״̬  
    private static int[][] tState = {  
        {1 ,2 ,3  } ,  
        {4,5,6  } ,  
        {7,8,0}  
    };  
      
    private static int[][] sState = {  
        {4 ,8 ,7  } ,  
        {5 ,6 ,3 } ,  
        {1 ,0,2}   
    };  
      
    //��ʼ״̬  
//  private static int[][] sState = {  
//      {12,1 ,10,2 } ,  
//      {7 ,11,4 ,14} ,  
//      {5 ,0 ,9 ,15} ,  
//      {8 ,13,6 ,3}   
//  };  
      
    private static int blank_row,blank_column;  
      
    public IDA(int[][] state) {  
        SIZE = state.length;  
        targetPoints = new int[SIZE * SIZE][2];  
          
        this.sState = state;  
        //�õ��ո�����  
        for(int i=0;i<state.length;i++) {  
            for(int j=0;j<state[i].length;j++) {  
                if(state[i][j] == 0) {  
                    blank_row = i;  
                    blank_column = j;  
                    break;  
                }  
            }  
        }  
          
        //�õ�Ŀ�����������  
        for(int i=0;i<state.length;i++) {  
            for(int j=0;j<state.length;j++) {  
                targetPoints[tState[i][j]][0] = i; //����Ϣ  
                  
                targetPoints[tState[i][j]][1] = j; //����Ϣ  
            }  
        }  
    }  
      
    /** 
     * ��������Ŀɽ��� 
     * @param state ״̬ 
     */  
    private boolean canSolve(int[][] state) {  
        if(state.length % 2 == 1) { //������Ϊ����  
            return (getInversions(state) % 2 == 0);  
        } else { //������Ϊż��  
            if((state.length - blank_row) % 2 == 1) { //�ӵ�������,�ո�λ��������  
                return (getInversions(state) % 2 == 0);  
            } else { //�ӵ�������,��λλ��ż����  
                return (getInversions(state) % 2 == 1);  
            }  
        }  
    }  
      
    public static void main(String[] args) {  
    	long time = System.currentTimeMillis();    
        IDA idaAlgorithm = new IDA(sState);  
        if(idaAlgorithm.canSolve(sState)) {  
            System.out.println("--����ɽ⣬��ʼ���--");  
            //�������پ���Ϊ��ʼ��С������  
            int j = idaAlgorithm.getHeuristic(sState);  
            System.out.println("��ʼmanhattan����:" + j);  
            int i = -1;//�ÿ�Ĭ���ƶ�����  
              
            
            //��������"��С������"  
            for(ans=j;;ans++) {  
                if(idaAlgorithm.solve(sState  
                        ,blank_row,blank_column,0,i,j)) {  
                    break;  
                }  
            }  
            System.out.println("�����ʱ:"+(System.currentTimeMillis() - time));  
  
            idaAlgorithm.printMatrix(sState);  
            int[][] matrix = idaAlgorithm.move(sState,moves[0]);  
            for(int k=1;k<ans;k++) {  
                matrix = idaAlgorithm.move(matrix, moves[k]);  
            }  
              
        } else {  
            System.out.println("--��Ǹ������������޿��н�--");  
        }  
    }  
      
    public int[][] move(int[][]state,int direction) {  
        int row = 0;  
        int column = 0;  
        for(int i=0;i<state.length;i++) {  
            for(int j=0;j<state.length;j++) {  
                if(state[i][j] == 0) {  
                    row = i;  
                    column = j;  
                }  
            }  
        }  
        switch(direction) {  
        case UP:  
            state[row][column] = state[row-1][column];  
            state[row-1][column] = 0;  
            break;  
        case DOWN:  
            state[row][column] = state[row+1][column];  
            state[row+1][column] = 0;  
            break;  
        case LEFT:  
            state[row][column] = state[row][column-1];  
            state[row][column-1] = 0;  
            break;  
        case RIGHT:  
            state[row][column] = state[row][column+1];  
            state[row][column+1] = 0;  
            break;  
        }  
        printMatrix(state);  
        return state;  
    }  
      
    public void printMatrix(int[][] matrix) {  
        System.out.println("------------");  
        for(int i=0;i<matrix.length;i++) {  
            for(int j=0;j<matrix.length;j++) {  
                System.out.print(matrix[i][j] + " ");  
            }  
            System.out.println();  
        }  
    }  
      
    /** 
     * ��ⷽ�� 
     * @param state ��ǰ״̬ 
     * @param blank_row ��λ�������� 
     * @param blank_column �ո�������� 
     * @param dep ��ǰ��� 
     * @param d ��һ���ƶ��ķ��� 
     * @param h ��ǰ״̬���ۺ��� 
     * @return 
     */  
    public boolean solve(int[][] state,int blank_row,int blank_column,  
            int dep,long d,long h) {  
          
        long h1;  
          
        //��Ŀ�����Ƚϣ����Ƿ���ͬ�������ͬ���ʾ�����ѽ�  
        boolean isSolved = true;  
        for(int i=0;i<SIZE;i++) {  
            for(int j=0;j<SIZE;j++) {  
                if(state[i][j] != tState[i][j]) {  
                    isSolved = false;  
                }  
            }  
        }  
        if(isSolved) {  
            return true;  
        }  
          
        if(dep == ans) {  
            return false;  
        }  
  
        //���ڱ�ʾ"�ո�"�ƶ��������λ��  
        int blank_row1 = blank_row;  
        int blank_column1  = blank_column;  
        int[][] state2 = new int[SIZE][SIZE];  
  
        for(int direction=0;direction<4;direction++) {  
            for(int i=0;i<state.length;i++) {  
                for(int j=0;j<state.length;j++) {  
                    state2[i][j] = state[i][j];  
                }  
            }  
              
            //�����ƶ�������ϴ��ƶ�����պ��෴�������������������  
            if(direction != d && (d%2 == direction%2)) {  
                continue;  
            }  
              
            if(direction == UP) {  
                blank_row1 = blank_row + up[0];  
                blank_column1 = blank_column + up[1];  
            } else if(direction == DOWN) {  
                blank_row1 = blank_row + down[0];  
                blank_column1 = blank_column + down[1];  
            } else if(direction == LEFT) {  
                blank_row1 = blank_row + left[0];  
                blank_column1 = blank_column + left[1];  
            } else {  
                blank_row1 = blank_row + right[0];  
                blank_column1 = blank_column + right[1];  
            }  
              
            //�߽���  
            if(blank_column1 < 0 || blank_column1 == SIZE  
                    || blank_row1 < 0 || blank_row1 == SIZE) {  
                continue ;  
            }  
              
            //�����ո�λ�ú͵�ǰ�ƶ�λ�ö�Ӧ�ĵ�Ԫ��     
            state2[blank_row][blank_column] = state2[blank_row1][blank_column1];  
            state2[blank_row1][blank_column1] = 0;  
              
            //�鿴��ǰ�ո��Ƿ����ڿ���Ŀ���  
            if(direction == DOWN && blank_row1   
                    > targetPoints[state[blank_row1][blank_column1]][0]) {  
                h1 = h - 1;  
            } else if(direction == UP && blank_row1   
                    < targetPoints[state[blank_row1][blank_column1]][0]){  
                h1 = h - 1;  
            } else if(direction == RIGHT && blank_column1   
                    > targetPoints[state[blank_row1][blank_column1]][1]) {  
                h1 = h - 1;  
            } else if(direction == LEFT && blank_column1   
                    < targetPoints[state[blank_row1][blank_column1]][1]) {  
                h1 = h - 1;  
            } else {   
                //�������������������ܵ��ƶ����򶼻�ʹ�ù��ۺ���ֵ���  
                h1 = h + 1;  
            }  
              
            if(h1+dep+1>ans) { //��֦  
                continue;  
            }  
              
            moves[dep] = direction;  
              
            //����������  
            if(solve(state2, blank_row1, blank_column1, dep+1, direction, h1)) {  
                return true;  
            }  
        }  
        return false;  
    }  
      
    /** 
     * �õ����ۺ���ֵ 
     */  
    public int getHeuristic(int[][] state) {  
        int heuristic = 0;  
        for(int i=0;i<state.length;i++) {  
            for(int j=0;j<state[i].length;j++) {  
                if(state[i][j] != 0) {  
                    heuristic = heuristic +   
                            Math.abs(targetPoints[state[i][j]][0] - i)  
                            + Math.abs(targetPoints[state[i][j]][1] - j);  
                      
                }  
            }  
        }  
        return heuristic;  
    }  
      
    /** 
     * ���������"���ñ�����" 
     * @param state 
     */  
    private int getInversions(int[][] state) {  
        int inversion = 0;  
        int temp = 0;  
        for(int i=0;i<state.length;i++) {  
            for(int j=0;j<state[i].length;j++) {  
                int index = i* state.length + j + 1;  
                while(index < (state.length * state.length)) {  
                    if(state[index/state.length][index%state.length] != 0   
                            && state[index/state.length]  
                                    [index%state.length] < state[i][j]) {  
                        temp ++;  
                    }  
                    index ++;  
                }  
                inversion = temp + inversion;  
                temp = 0;  
            }  
        }  
        return inversion;  
    }  
}  
