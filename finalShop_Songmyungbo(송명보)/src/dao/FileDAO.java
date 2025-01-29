package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import controller.MallController;
import dto.Board;
import dto.Cart;
import dto.Item;
import dto.Member;

public class FileDAO {
	private static final String FILE_PATH = System.getProperty("user.dir") + "\\src\\files\\";

    enum FileName {
        BOARD("board.txt"), MEMBER("member.txt"), ITEM("item.txt"), CART("cart.txt");
        private String name;

        FileName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private FileDAO() {}

    private static FileDAO instance = new FileDAO();

    static public FileDAO getInstance() {
        return instance;
    }

    // 파일 저장 메서드
    public void saveToFile() {
    	saveData(FileName.BOARD, BoardDAO.getInstance().getSaveData());
    	saveData(FileName.ITEM, ItemDAO.getInstance().getSaveData());
    	saveData(FileName.CART, CartDAO.getInstance().getSaveData());
    	saveData(FileName.MEMBER, MemberDAO.getInstance().getSaveData());
    }

    private void saveData(FileName name, String data) {
    	if(data == null) {
    		System.out.println("데이터 저장 실패");
    		return;
    	}
    	try(FileWriter fw = new FileWriter(FILE_PATH + name.getName())){
    		fw.write(data);
    		System.out.println("데이터 저장 성공");
    	} catch (IOException e) {
    		System.out.println("데이터 저장 실패");
		}
	}

	public void loadAllFiles() {
		BoardDAO.getInstance().setLoadData(getLoadData(FileName.BOARD));
		ItemDAO.getInstance().setLoadData(getLoadData(FileName.ITEM));
		CartDAO.getInstance().setLoadData(getLoadData(FileName.CART));
		MemberDAO.getInstance().setLoadData(getLoadData(FileName.MEMBER));
    }

	private String getLoadData(FileName name) {
		String data = "";
		try(FileReader fr = new FileReader(FILE_PATH + name.getName()); BufferedReader br = new BufferedReader(fr)){
			while(true) {
				String temp = br.readLine();
				if(temp == null) break;
				data += temp + "\n";
			}
			System.out.println("데이터 로드 완료");
			return data;
		} catch (FileNotFoundException e) {
			System.out.println("데이터 파일이 없습니다!");
		} catch (IOException e) {
			System.out.println("데이터 로드 실패");
		}
		
		return null;
	}

}