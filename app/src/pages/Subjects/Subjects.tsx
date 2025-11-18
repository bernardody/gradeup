import StudentSideBar from "../SideBar/Student-SideBar";
import "./Subjects.css";


export default function Subjects(){
    return (
        <div className="subjects-container">
            <div className="subject-couple">
                <div className="subject-single">
                    <p>Matemática</p>
                </div>
                 <div className="subject-single">
                    <p>Matemática</p>
                 </div>  
                 <div className="subject-single">
                    <p>Matemática</p>
                </div> 
                <div className="subject-single">
                    <p>Matemática</p>
                </div>  
            </div>

            <div className="subject-couple">
                <div className="subject-single">
                    <p>Matemática</p>
                </div> 
                <div className="subject-single">
                    <p>Matemática</p>
                </div> 
                <div className="subject-single">
                    <p>Matemática</p>
                </div> 
                <div className="subject-single">
                    <p>Matemática</p>
                </div>  
            </div>

               <div className="subject-couple">
                <div className="subject-single">
                    <p>Matemática</p>
                </div> 
                <div className="subject-single">
                    <p>Matemática</p>
                </div> 
                <div className="subject-single">
                    <p>Matemática</p>
                </div> 
                <div className="subject-single">
                    <p>Matemática</p>
                </div>  
            </div>
            
            
         <div className="sideBar">
            <StudentSideBar />
        </div> 
        </div> 
    )
}