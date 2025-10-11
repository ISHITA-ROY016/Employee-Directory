package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public  EmployeeController(EmployeeService theEmpl) {
        employeeService = theEmpl;
    }

    // add mapping for "/list"
    @GetMapping("/list")
    public String listEmployees(Model theModel) {
        // get employee from db
        List<Employee> employees = employeeService.findAll();

        // add that to the spring model
        theModel.addAttribute("employees", employees);

        return "employees/list-employees";
    }

    //
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    //
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmpl) {
        // save the employee
        employeeService.save(theEmpl);

        // use a redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    //
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {

        // get employee from the service
        Employee theEmpl = employeeService.findById(theId);

        // set employee in the model to prepopulate the form
        theModel.addAttribute("employee",theEmpl);

        // send over to our form
        return "employees/employee-form";
    }

    //
    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {
        // delete the employee
        employeeService.deleteById(theId);

        // redirect to the /employees/list
        return "redirect:/employees/list";

    }
}
