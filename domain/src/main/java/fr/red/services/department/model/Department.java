package fr.red.services.department.model;

public class Department {

    private String deptno;
    private String dname;
    private String loc;

    public Department(String deptno, String dname, String loc) {
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }

    public String getDeptno() {
        return deptno;
    }

    public String getDname() {
        return dname;
    }

    public String getLoc() {
        return loc;
    }

    public static DepartmentBuilder builder() {
        return new DepartmentBuilder();
    }

    public static class DepartmentBuilder {

        private String deptno;
        private String dname;
        private String loc;

        DepartmentBuilder() {
        }

        public DepartmentBuilder deptno(String deptno) {
            this.deptno = deptno;
            return this;
        }

        public DepartmentBuilder dname(String dname) {
            this.dname = dname;
            return this;
        }

        public DepartmentBuilder loc(String loc) {
            this.loc = loc;
            return this;
        }

        public Department build() {
            return new Department(deptno, dname, loc);
        }

    }
}
