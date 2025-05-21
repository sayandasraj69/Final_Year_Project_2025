

  
const allDoctors = [];

const doctorList = document.getElementById("doctors-list");

fetch("http://localhost:8080/all")
  .then(response => response.json())
  .then(data => {
    allDoctors.push(...data);
    displayDoctors(allDoctors);
    console.log(allDoctors);
  })
  .catch(error => console.error("Error fetching doctors:", error));

// Function to display doctors
function displayDoctors(doctors) {
  doctorList.innerHTML = ""; // Clear previous results
  if (doctors.length === 0) {
    doctorList.innerHTML = "<p>No doctors found.</p>";
    return;
  }

  doctors.forEach(doctor => {
    const doctorName = doctor.doctorName || "Unknown Name";
    const specializations = doctor.specializations || []; // Assuming specializations is a property

    const doctorDiv = document.createElement("div");
    doctorDiv.className = "doctor";
    
    const specializationText = specializations.length > 0 
      ? specializations.join(", ") 
      : "No specialization listed";

    doctorDiv.innerHTML = `
      <h3>${doctorName}</h3>
      <p>Specializations: ${specializationText}</p>
    `;

    doctorList.appendChild(doctorDiv);
  });
}