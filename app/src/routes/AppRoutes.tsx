import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login/Login";
import HomeStudent from "../pages/Home/HomeStudent";
import HomeTeacher from "../pages/Home/HomeTeacher";
import Subjects from "../pages/Subjects/Subjects";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/HomeStudent" element={<HomeStudent />} />
      <Route path="/HomeTeacher" element={<HomeTeacher />} />
      <Route path="/Subjects" element={<Subjects />} />
    </Routes>
  );
}
