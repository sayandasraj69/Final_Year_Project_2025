let allDoctors = null;

// Get doctorId from URL
const doctorId = new URLSearchParams(window.location.search).get("docId");

// Main function to fetch and display all doctor details
function showDoctorDetails() {
    fetch(`http://localhost:8080/doctor/${doctorId}/details`)
        .then(response => response.json())
        .then(doctor => {
            // console.log('doctor.degrees:', doctor.degrees);
            if (!doctor) {
                document.querySelector(".doctor-details").innerHTML = "<p>Doctor not found.</p>";
                return;
            }

            // Photo
            if (doctor.docImageData) {
                document.getElementById('doctor-photo').src = `data:${doctor.docImageType};base64,${doctor.docImageData}`;
            }

            // Name
            document.getElementById('doctor-name').textContent = doctor.docName || '';

            // Degrees
            document.getElementById('doctor-degree').innerHTML = (doctor.degrees || [])
                .map(degree => `<span class="degree-tag">${degree}</span>`).join(' ');

            // Specializations
            document.getElementById('doctor-specialization').innerHTML = (doctor.specializations || [])
                .map(spec => `<span class="tag">${spec}</span>`).join(' ');

            // About
            document.getElementById('doctor-about').textContent = doctor.about || '';

            // Experience
            document.getElementById('doctor-experience').textContent = doctor.experience ? `${doctor.experience} years` : '';

            // Fee
            document.getElementById('doctor-fee').textContent = doctor.fee || '';

            // Contact
            document.getElementById('doctor-contact').innerHTML = `
              <div>Email: ${doctor.docEmail || ''}</div>
              <div>Phone: ${doctor.docPhn || ''}</div>
            `;

            // Address (optional, if you have address fields)
            document.getElementById('doctor-address').textContent = doctor.address || '';

            // Schedule
            const scheduleDiv = document.getElementById('doctor-schedule');
            scheduleDiv.innerHTML = '';
            (doctor.schedules || []).forEach(day => {
                const dayBlock = document.createElement('div');
                dayBlock.className = 'schedule-day';
                dayBlock.innerHTML = `<strong>${day.scheduleDay}:</strong>`;
                const timingsList = document.createElement('ul');
                (day.timings || []).forEach(t => {
                    const li = document.createElement('li');
                    li.innerHTML = `
                      ${t.timingRange} (${t.center}, ${t.city}) - Max Patients: ${t.noOfPatients}
                    `;
                    timingsList.appendChild(li);
                });
                dayBlock.appendChild(timingsList);
                scheduleDiv.appendChild(dayBlock);
            });
        })
        .catch(error => {
            console.error("Error fetching doctor details:", error);
            document.querySelector(".doctor-details").innerHTML = "<p>Error fetching doctor details.</p>";
        });
}

// After your showDoctorDetails() function definition

function fetchAllDoctorsAndShowSimilar() {
    fetch('http://localhost:8080/doctor/all') // <-- Adjust endpoint if needed
        .then(response => response.json())
        .then(data => {
            allDoctors = data;
            showSimilarDoctors();
        })
        .catch(error => {
            console.error("Error fetching all doctors:", error);
        });
}

// Call this after loading the main doctor details
showDoctorDetails();
fetchAllDoctorsAndShowSimilar();

// show similar doctors search by similar specialization
function showSimilarDoctors() {
    if (!allDoctors) {
        console.error("All doctors data is not available.");
        return;
    }

    // Get current doctor's specializations as an array
    const currentSpecs = Array.from(document.querySelectorAll('#doctor-specialization .tag')).map(tag => tag.textContent.trim());
    const currentDoctorId = doctorId;

    // Filter doctors with at least one matching specialization, excluding current doctor
    const similarDoctors = allDoctors.filter(doc => {
        if (String(doc.docId) === String(currentDoctorId)) return false;
        if (!doc.specializations) return false;
        return doc.specializations.some(spec => currentSpecs.includes(spec));
    });

    const similarDoctorsList = document.getElementById("similar-doctors-list");
    similarDoctorsList.innerHTML = ""; // Clear previous results

    if (similarDoctors.length === 0) {
        similarDoctorsList.innerHTML = "<p>No similar doctors found.</p>";
        return;
    }

    similarDoctors.forEach(doctor => {
        const card = document.createElement("div");
        card.className = "doctor-card";
        card.innerHTML = `
            <img src="${doctor.docImageData ? `data:${doctor.docImageType};base64,${doctor.docImageData}` : 'default-doctor.png'}" alt="Doctor Photo" class="doctor-card-photo">
            <div class="doctor-card-info">
                <h3>${doctor.docName || "Unknown Name"}</h3>
                <div class="doctor-card-specializations">
                    ${(doctor.specializations || []).map(spec => `<span>${spec}</span>`).join(' ')}
                </div>
                <div class="doctor-card-degree">Degrees: ${(doctor.degrees || []).join(', ')}</div>
                <div class="doctor-card-experience">Experience: ${doctor.experience || 0} years</div>
            </div>
        `;
        card.addEventListener("click", () => {
            window.location.href = `docDetail.html?docId=${doctor.docId}`;
        });
        similarDoctorsList.appendChild(card);
    });
}

console.log("allDoctors:", allDoctors);

allDoctors.forEach(doc => {
    console.log(doc.docId, doc.specializations);
});

// // Example: fetch doctor details by ID (replace with actual doctor ID logic)
// fetch('/api/doctors/1/details')
//   .then(res => res.json())
//   .then(data => {
//     // Photo
//     if (data.docImageData) {
//       document.getElementById('doctor-photo').src = `data:${data.docImageType};base64,${data.docImageData}`;
//     }

//     // Name
//     document.getElementById('doctor-name').textContent = data.docName || '';

//     // Degrees
//     document.getElementById('doctor-degree').textContent = (data.degrees || []).join(', ');

//     // Specializations
//     document.getElementById('doctor-specialization').innerHTML = (data.specializations || [])
//       .map(spec => `<span class="tag">${spec}</span>`).join(' ');

//     // About
//     document.getElementById('doctor-about').textContent = data.about || '';

//     // Experience
//     document.getElementById('doctor-experience').textContent = data.experience ? `${data.experience} years` : '';

//     // Fee
//     document.getElementById('doctor-fee').textContent = data.fee || '';

//     // Contact
//     document.getElementById('doctor-contact').innerHTML = `
//       <div>Email: ${data.docEmail || ''}</div>
//       <div>Phone: ${data.docPhn || ''}</div>
//     `;

//     // Address (optional, if you have address fields)
//     document.getElementById('doctor-address').textContent = data.address || '';

//     // Schedule
//     const scheduleDiv = document.getElementById('doctor-schedule');
//     scheduleDiv.innerHTML = '';
//     (data.schedules || []).forEach(day => {
//       const dayBlock = document.createElement('div');
//       dayBlock.className = 'schedule-day';
//       dayBlock.innerHTML = `<strong>${day.scheduleDay}:</strong>`;
//       const timingsList = document.createElement('ul');
//       (day.timings || []).forEach(t => {
//         const li = document.createElement('li');
//         li.innerHTML = `
//           ${t.timingRange} (${t.center}, ${t.city}) - Max Patients: ${t.noOfPatients}
//         `;
//         timingsList.appendChild(li);
//       });
//       dayBlock.appendChild(timingsList);
//       scheduleDiv.appendChild(dayBlock);
//     });
//   })
//   .catch(err => {
//     // Handle error
//     console.error('Failed to load doctor details', err);
//   });


