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
import ExamList from "../pages/TeacherExamsList/ExamList";
import GradeManagement from "../pages/TeacherExamsList/GradeManagement";
import AllTeacherWarnings from "../pages/Warnings/AllTeacherWarnings";


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
      <Route path="/class/:id/exam-list" element={<ExamList/>} />
        <Route path="/exams/:id" element={<ExamList />} />
 <Route path="/grades/manage/:examId" element={<GradeManagement />} />
 <Route path="/class/:id/warnings" element={<AllTeacherWarnings />} />
    </Routes>
  );
}
