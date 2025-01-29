package menu_member;

import _mall.MenuCommand;
import controller.MallController;
import dao.MemberDAO;
import util.Util;

public class MemberInfo implements MenuCommand {
    private MallController cont;

    @Override
    public void init() {
        cont = MallController.getInstance();
        System.out.println("==========[ 내 정보 ]==========");
        showUserInfo(); // 사용자 정보 출력
    }

    @Override
    public boolean update() {
        int sel = Util.getValue("[1] 비밀번호변경\n[2] 뒤로가기\n[0] 종료", 0, 2);
        if(sel == 1) {
        	changePassword();
        }else if(sel == 2){
        	cont.setNext("MemberMain");
        }else if(sel == 0) {
        	cont.setNext(null);
        }
        return false;
        
    }

    private void showUserInfo() {
        String loginUserId = cont.getLoginId();
        MemberDAO memberDAO = MemberDAO.getInstance();
        memberDAO.printInfo(loginUserId);
    }

    private void changePassword() {
        String loginUserId = cont.getLoginId();
        MemberDAO memberDAO = MemberDAO.getInstance();

        // 현재 비밀번호 입력
        String currentPassword = Util.getValue("패스워드 ");
        
        // 새 비밀번호 입력
        String newPassword = Util.getValue("신규 패스워드 ");

        // 비밀번호 변경
        if (memberDAO.changePassword(loginUserId, currentPassword, newPassword)) {
            System.out.println("비밀번호 변경완료");
        } else {
            System.out.println("비밀번호 변경에 실패했습니다.");
        }
    }
}

