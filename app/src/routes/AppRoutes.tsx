import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "../pages/Login/Login";
import HomeStudent from "../pages/Home/HomeStudent";
import HomeTeacher from "../pages/Home/HomeTeacher";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/HomeStudent" element={<HomeStudent />} />
        <Route path="/HomeTeacher" element={<HomeTeacher />} />
      </Routes>
    </BrowserRouter>
  );
}
