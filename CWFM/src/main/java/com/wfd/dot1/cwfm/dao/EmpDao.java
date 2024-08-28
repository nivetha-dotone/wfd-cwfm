package com.wfd.dot1.cwfm.dao;
import java.util.List;

import com.wfd.dot1.cwfm.entity.Emp;

public interface EmpDao {

	public int saveEmp(Emp emp);

	public Emp getEmpById(int id);

	public List<Emp> getAllEmp();

	public void update(Emp emp);

	public void deleteEmp(int id);

}
