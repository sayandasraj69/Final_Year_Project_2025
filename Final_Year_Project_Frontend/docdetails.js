let allDoctors = null;

// Get doctorId from URL
const doctorId = new URLSearchParams(window.location.search).get("docId");

// Main function to fetch and display all doctor details
function showDoctorDetails() {
    return fetch(`http://localhost:8080/doctor/${doctorId}/details`)
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
            console.log("allDoctors:", allDoctors);
            allDoctors.forEach(doc => {
                console.log(doc.docId, doc.specializations);
            });
            showSimilarDoctors();
        })
        .catch(error => {
            console.error("Error fetching all doctors:", error);
        });
}

// Then chain the calls:
showDoctorDetails().then(fetchAllDoctorsAndShowSimilar);

// show similar doctors search by similar specialization via POST request
function showSimilarDoctors() {
    // Get current doctor's specializations as an array
    const currentSpecs = Array.from(document.querySelectorAll('#doctor-specialization .tag')).map(tag => tag.textContent.trim());
    const currentDoctorId = doctorId;

    // Prepare the POST body
    const postData = {
        specializations: currentSpecs,
        excludeDoctorId: currentDoctorId
    };

    fetch('http://localhost:8080/doctor/similar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(postData)
    })
    .then(response => response.json())
    .then(similarDoctors => {
        const similarDoctorsList = document.getElementById("similar-doctors-list");
        similarDoctorsList.innerHTML = ""; // Clear previous results

        if (!similarDoctors || similarDoctors.length === 0) {
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
                    <div class="doctor-card-fee">Fee: ${doctor.fee ? doctor.fee : 'N/A'}</div>
                </div>
            `;
            card.addEventListener("click", () => {
                window.location.href = `docDetail.html?docId=${doctor.docId}`;
            });
            similarDoctorsList.appendChild(card);
        });
    })
    .catch(error => {
        console.error("Error fetching similar doctors:", error);
        document.getElementById("similar-doctors-list").innerHTML = "<p>Error fetching similar doctors.</p>";
    });
}


