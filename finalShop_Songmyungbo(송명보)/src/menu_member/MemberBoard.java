package menu_member;

import _mall.MenuCommand;
import controller.MallController;
import dao.BoardDAO;
import util.Util;

public class MemberBoard implements MenuCommand {
    private MallController cont;
    BoardDAO dao = BoardDAO.getInstance();
    private String loginId;  // 로그인된 사용자 ID

    public MemberBoard(String UserId) {
        this.loginId = UserId;
    }

    @Override
    public void init() {
        cont = MallController.getInstance();
        System.out.println("로그인된 사용자 ID: " + loginId);
        System.out.println("======[ 게시판 ]======");
        System.out.println("[1] 게시글보기\n[2] 게시글추가\n[3] 내게시글(삭제)\n[4] 뒤로가기\n[0] 종료");
        System.out.println("=====================");
    }

    @Override
    public boolean update() {
        int sel = Util.getValue("메뉴", 0, 4);
        if (sel == 0) {
            cont.setNext(null);
        } else if (sel == 1) {
            dao.showBoard();
        } else if (sel == 2) {
            dao.addBoard(loginId);  // 로그인된 사용자의 ID를 넘겨주어 게시글 추가
        } else if (sel == 3) {
            dao.showMyPosts(loginId);
        } else if (sel == 4) {
            cont.setNext("MemberMain");
        }
        return false;
    }
}
