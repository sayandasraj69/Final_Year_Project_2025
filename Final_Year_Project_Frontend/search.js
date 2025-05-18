const doctorList = document.getElementById("doctors");
const showAllButton = document.getElementById("search-button");

showAllButton.addEventListener("click", loadDoctor);

async function loadDoctor() {
  try {
    const response = await fetch("http://localhost:8080/all");
    const doctors = await response.json();
    console.log(doctors);

    doctorList.innerHTML = ""; // Clear previous results
    if (doctors.length === 0) {
      doctorList.innerHTML = "<p>No doctors found.</p>";
      return;
    }
    doctors.forEach(doctor => {
      // Adjust property access based on your API response structure
      const name = doctor[1] || "unnamed doctor";
      const specialization = doctor[0] || "unknown specialization";
      

      const doctorDiv = document.createElement("div");
      doctorDiv.className = "doctor";
      doctorDiv.innerHTML = `
        <h3>${name}</h3>
        <p>Specialization: ${specialization}</p>
        ${experience ? `<p>Experience: ${experience} years</p>` : ""}
        ${contact ? `<p>Contact: ${contact}</p>` : ""}
      `;
      doctorList.appendChild(doctorDiv);
    });
  } catch (error) {
    console.error("Error fetching doctors:", error);
    doctorList.innerHTML = "<p>Error fetching doctors.</p>";
  }
}