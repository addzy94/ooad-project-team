public abstract class Staff {

    String name;
    int daysWorkedInARow;
    boolean isActiveWorker;

    Staff() {}

    Staff (String name, int daysWorkedInARow) {
        this.name = name;
        this.daysWorkedInARow = daysWorkedInARow;
        this.isActiveWorker = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDaysWorkedInARow() {
        return daysWorkedInARow;
    }

    public void setDaysWorkedInARow(int daysWorkedInARow) {
        this.daysWorkedInARow = daysWorkedInARow;
    }

    public boolean getIsActiveWorker() {
        return isActiveWorker;
    }

    public void setIsActiveWorker(boolean isActiveWorker) {
        this.isActiveWorker = isActiveWorker;
    }

    public void incrementDaysWorkedInARow(Store s) {
        int daysWorkedCurrently = this.getDaysWorkedInARow();
        for (Staff staff: s.getStaff()) {
            staff.setDaysWorkedInARow(0);
        }
        this.daysWorkedInARow = daysWorkedCurrently + 1;
    }
}
