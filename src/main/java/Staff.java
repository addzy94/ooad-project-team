public abstract class Staff {

    String name;
    int daysWorkedInARow;
    boolean isActiveWorker;
    boolean isSick;

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

    public void setSick(boolean isSick) {
        this.isSick = isSick;
    }

    public boolean getSick() {
        return isSick;
    }

    public void incrementDaysWorkedInARow(Store s) {

        // Get the current clerk's work_day value who's going to work for today
        int tempWorkedDays = this.daysWorkedInARow;
        // Assign every clerk's work day to 0 first
        for (Staff staff: Store.getStaff()) {
            staff.setDaysWorkedInARow(0);
        }
        // Then assign the current worker's working day to temp_day + 1
        this.daysWorkedInARow = tempWorkedDays + 1;
    }
}
