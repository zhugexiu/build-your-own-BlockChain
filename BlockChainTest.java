import java.util.ArrayList;

public class BlockChainTest {

	public static ArrayList<Block> blockChain = new ArrayList<>();
	
	/**
	 * 挖矿的难度，只有前几个是0的才合法
	 */
	public static int difficulty = 5;
	
	public static void main(String[] args) {
		Block firstBlock = new Block("first block", "0");
		firstBlock.mineBlock(5);
		System.out.println(" first block hash: " + firstBlock.hash);
		
		Block secondBlock = new Block("second block", firstBlock.hash);
		secondBlock.mineBlock(5);
		System.out.println("second block hash: " + secondBlock.hash);
		
		Block thirdBlock = new Block("third block", secondBlock.hash);
		thirdBlock.mineBlock(5);
		System.out.println(" third block hash: " + thirdBlock.hash);
		
		blockChain.add(new Block("first block", "0"));
		blockChain.get(0).mineBlock(difficulty);
		
		blockChain.add(new Block("second block", blockChain.get(blockChain.size() - 1).hash));
		blockChain.get(1).mineBlock(difficulty);
		
		blockChain.add(new Block("third block", blockChain.get(blockChain.size() - 1).hash));
		blockChain.get(2).mineBlock(difficulty);
		
		System.out.println("Is BlockChain valid: " + isChainValid());
		System.out.println(JsonUtil.toJson(blockChain));
		
		
	}

	public static Boolean isChainValid(){

        Block currentBlock;
        Block previousBlock;
        boolean flag = true;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        
        for(int i=1;i<blockChain.size();i++){
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);
           
            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("当前hash不相等");
                flag=false;
            }
            
            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("前一个hash不相等");
                flag=false;
            }

            
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("这个区块还没有被开采，也就是你这个区块他不是合格的");
                flag=false;
            }
        }

        return flag;
    }
}
