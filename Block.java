import java.util.Date;

public class Block {

	public String hash; 
	
	/**
	 * 前一个区块的hash,靠这个实现链
	 */
	
	public String previousHash;
	
	/**
	 * 当前区块的数据
	 */
	private String data;
	
	/**
	 * 时间戳， 表示事件发生的时间信息
	 */
	private long timeStamp;
	
	private int nonce;
	
	public Block(String hash, String previousHash, String data) {
		this.data = data;
		this.hash = hash;
		this.previousHash = previousHash;
	}
	
	public Block(String data, String previousHash) {
		this.previousHash = previousHash;
		this.data = data;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256(previousHash + 
				Long.toString(timeStamp) + Integer.toString(nonce) + data);
		return calculatedhash;
	}
	/**
	 * 不停执行hash运算， 直到算出符合条件的区块，nonce就是改区块所需要的次数
	 * @param difficulty
	 */
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
	}

}
