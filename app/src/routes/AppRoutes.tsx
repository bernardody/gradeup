import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login/Login";
import HomeStudent from "../pages/Home/HomeStudent";
import HomeTeacher from "../pages/Home/HomeTeacher";
import Subjects from "../pages/Subjects/Subjects";
import SubjectDetails from "../pages/Subjects/SubjectDetails";
import Classes from "../pages/Classes/Classes";
import Class from "../pages/Class/Class";
import Exam from "../pages/Exam/Exam";
import { Navigate } from "react-router-dom";
import SelectSubject from "../pages/Subjects/SelectSubject";
import AllStudentWarnings from "../pages/Warnings/AllStudentWarnings";


export default function AppRoutes() {

  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/login" element={<Login />} />
      <Route path="/HomeStudent" element={<HomeStudent />} />
      <Route path="/HomeTeacher" element={<HomeTeacher />} />
      <Route path="/subjects" element={<Subjects />} />
      <Route path="/subjects/:subjectName" element={<SubjectDetails />} />
      <Route path="/classes" element={<Classes />} />
      <Route path="/class/:id" element={<Class />} />
      <Route path="/class/:id/exam" element={<Exam />} />
      <Route path="/class/:id/subjects" element={<SelectSubject />} />
      <Route path="/student/avisos" element={<AllStudentWarnings />} />

    </Routes>
  );
}
