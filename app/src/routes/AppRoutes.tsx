import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login/Login";
import HomeStudent from "../pages/Home/HomeStudent";
import HomeTeacher from "../pages/Home/HomeTeacher";
import Subjects from "../pages/Subjects/Subjects";
import SubjectDetails from "../pages/Subjects/SubjectDetails";
import Classes from "../pages/Classes/Classes";
import Class from "../pages/Class/Class";
import Exam from "../pages/Exam/Exam";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/HomeStudent" element={<HomeStudent />} />
      <Route path="/HomeTeacher" element={<HomeTeacher />} />
      <Route path="/Subjects" element={<Subjects />} />
      <Route path="/subjects/:subjectName" element={<SubjectDetails />} />
      <Route path="/classes" element={<Classes />} />
      <Route path="/class/:id" element={<Class />} /> {}
      <Route path="/class/:id/exam" element={<Exam />} />

    </Routes>
  );
}
