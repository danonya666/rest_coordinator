package Domain;

import java.util.ArrayList;
import java.time.LocalDate;
public class Attendance {
    private Cost cost_;
    private int rating_; // rating is from 0 to 10
    private LocalDate date_;
    private int clientId_;
    private int restarauntId_;
    public Attendance(Cost cost_, int rating_, LocalDate date, int clientId_, int restarauntId_,
                      ArrayList<Attendance> attendanceArrayList) {
        this.cost_ = cost_;
        this.rating_ = rating_;
        this.clientId_ = clientId_;
        this.restarauntId_ = restarauntId_;
        this.date_ = date;
        //Not adding attendance to attendanceArrayList to have a possibillity not to have attendance in the list
    }

    public void addToAttendanceList(){
        Attendance.AttendanceList.addAttendance(this);
    }

    public static class AttendanceList{
        private static ArrayList<Attendance> attendanceArrayList_;

        public static ArrayList<Attendance> getAttendanceArrayList_() {
            return attendanceArrayList_;
        }

        public static void addAttendance(Attendance attendance){
            attendanceArrayList_.add(attendance);
        }
    }
}
