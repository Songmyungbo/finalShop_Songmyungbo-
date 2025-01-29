package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Board;
import util.Util;

public class BoardDAO {
    private static BoardDAO instance;

    public static BoardDAO getInstance() {
        if (instance == null) instance = new BoardDAO();
        return instance;
    }

    List<Board> bList = new ArrayList<Board>();

    // 현재 페이지 상태
    private int currentPage = 1;  // 현재 페이지 번호
    private final int postsPerPage = 5;  // 한 페이지에 보여줄 게시글 수

    // 게시글 추가
    public void addBoard(String loginUserId) {
        String title = Util.getValue("제목");
        String contents = Util.getValue("내용");

        // 로그인된 ID로 작성자 설정
        bList.add(new Board(title, loginUserId, contents));
        System.out.println("게시글이 추가되었습니다.");
    }
    
    // 게시글 조회
    public void showBoard() {
        int totalPosts = bList.size();  // 전체 게시글 수
        int totalPages = (int) Math.ceil((double) totalPosts / postsPerPage);  // 전체 페이지 개수

        int startRow = (currentPage - 1) * postsPerPage;  // 현재 페이지의 시작 게시글 번호
        int endRow = startRow + postsPerPage;  // 현재 페이지의 끝 게시글 번호

        System.out.printf("총 게시글 %d개\n", totalPosts);
        System.out.printf("현재페이지 [%d / %d]\n", currentPage, totalPages);

        if (totalPosts > 0) {
            // 현재 페이지에 해당하는 게시글 출력
            for (int i = startRow; i < endRow; i++) {
            	if(i >= totalPosts) {
            		break;
            	}
                Board board = bList.get(i);
                System.out.printf("(%3d) [ 제목 : %s 작성자 : %s 날짜 : %s 조회수 : %d]\n",
                                   i + 1, board.getTitle(), board.getId(), board.getDate(), board.getHits());
            }
        } else {
            System.out.println("게시글이 없습니다.");
            return;  // 게시글이 없으면 메소드 종료
        }

        // 페이지 네비게이션
        System.out.println("[1]이전\n[2]이후\n[3]게시글보기[0]종료");
        int sel = Util.getValue("메뉴", 0, 3);
        if (sel == 1) {
            if (currentPage > 1) {
                currentPage--;
                showBoard();  // 이전 페이지 출력
            } else {
                System.out.println("이전페이지가 존재하지 않습니다.");
            }
        } else if (sel == 2) {
            if (currentPage < totalPages) {
                currentPage++;
                showBoard();  // 다음 페이지 출력
            } else {
                System.out.println("다음페이지가 존재하지 않습니다.");
            }
        } else if (sel == 3) {
            if (totalPosts > 0) {
                int postNumber = Util.getValue("게시글 번호", 1, totalPosts);
                Board selectedBoard = bList.get(postNumber - 1);

                // 조회수 증가
                selectedBoard.setHits(selectedBoard.getHits() + 1);

                System.out.println("게시글 제목: " + selectedBoard.getTitle());
                System.out.println("게시글 내용: " + selectedBoard.getContents());
                System.out.println("조회수: " + selectedBoard.getHits());
            }
        } else if (sel == 0) {
            System.out.println("종료합니다.");
        }
    }

    // 내 게시글 목록 출력 (로그인된 사용자의 게시글만)
    public void showMyPosts(String loginUserId) {
        boolean found = false;
        System.out.println("======[ 내 게시글 목록 ]======");
        for (Board board : bList) {
            if (board.getId().equals(loginUserId)) {  // 작성자 ID가 로그인된 ID와 일치하는 게시글만
            	System.out.println(board);
                found = true;
            }
        }

        if (!found) {
            System.out.println("본인의 게시글이 없습니다.");
            return;
        }

        System.out.println("[1]삭제 [0]돌아가기");
        int sel = Util.getValue("메뉴", 0, 1);
        if (sel == 1) {
            deleteBoard(loginUserId);
        }
    }

    // 게시글 삭제 (본인 게시글만)
    public void deleteBoard(String loginUserId) {
        int postNumber = Util.getValue("삭제할 게시글 번호 입력 ", 1, bList.size());

        Board board = bList.get(postNumber - 1);
        if (board.getId().equals(loginUserId)) {  // 본인 게시글만 삭제 가능
            bList.remove(postNumber - 1);
            System.out.println("게시글이 삭제되었습니다.");
            return;
        } else {
            System.out.println("본인 게시글만 삭제할 수 있습니다.");
            return;
        }
    }
    public void deleteBoardAdmin() {
    	if(bList.size() <= 0) {
    		System.out.println("삭제할 게시글이 없습니다");
    		return;
    	}else {
    		int postNumber = Util.getValue("삭제할 게시글 번호 입력 ", 1, bList.size());
    		bList.remove(postNumber - 1);
    		System.out.println("게시글 삭제 완료");
    		return;
    	}
    }

	public List<Board> getBoardList() {
		return bList;
	}
	
	public void setLoadData(String data) {
		if(data == null) {
			System.out.println("저장을 먼저하세요!");
			return;
		}
		String[] info = data.split("\n");
		for(int i = 0; i < info.length; i++) {
			String [] temp = info[i].split("/");
			bList.add(new Board(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3],temp[4],Integer.parseInt(temp[5])));
		}
	}
	
	public String getSaveData() {
		if(bList.size() <= 0) {
			System.out.println("게시판이 없습니다");
			return null;
		}
		String data = "";
		for(Board b : bList) {
			data += String.format("%d/%s/%s/%s/%s/%d\n", b.getBoradNum(),b.getTitle(),b.getId(),b.getContents(),b.getDate(),b.getHits());
		}
		return data.substring(0,data.length()-1);
	}
    
    
}