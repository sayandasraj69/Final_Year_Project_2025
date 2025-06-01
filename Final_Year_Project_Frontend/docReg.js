<<<<<<< HEAD
  // Populate specialization dropdown on page load
        fetch("http://localhost:8080/doctor/specialization_names")
            .then(response => response.json())
            .then(data => {
                const specializationDropdown = document.getElementById("specializations");
                data.forEach(specialization => {
                    const option = document.createElement("option");
                    option.value = specialization;
                    option.textContent = specialization;
                    specializationDropdown.appendChild(option);
                });
            })
            .catch(error => console.error("Error fetching specializations:", error));

        // (Do the same for qualification if needed)

        const form1E1 = document.querySelector("#registrationForm");
        form1E1.addEventListener("submit", event => {
            event.preventDefault(); // Prevent form submission

            // Validate form fields
            let isValid = true;

            // Validate Doctorname
            const docName = document.getElementById("docName");
            const doctornameError = document.getElementById("doctornameError");
            if (docName.value.trim() === "") {
                doctornameError.style.display = "block";
                isValid = false;
            } else {
                doctornameError.style.display = "none";
            }

            // Validate email
            const docEmail = document.getElementById("docEmail");
            const emailError = document.getElementById("emailError");
            if (docEmail.value.trim() === "") {
                emailError.style.display = "block";
                isValid = false;
            } else {
                emailError.style.display = "none";
            }

            // Validate qualification
            // const degrees = document.getElementById("deegrees");
            // const qualificationError = document.getElementById("qualificationError");
            // if (degrees.value.trim() === "") {
            //     qualificationError.style.display = "block";
            //     isValid = false;
            // } else {
            //     qualificationError.style.display = "none";
            // }

            // Validate specialization
            const specialization = document.getElementById("specializations");
            const specializations = {
                "specialization1":specialization
            }
            const specializationError = document.getElementById("specializationError");
            if (specializations[`specialization1`].value.trim() === "") {
                specializationError.style.display = "block";
                isValid = false;
            } else {
                specializationError.style.display = "none";
            }

            // Uncomment and validate password if needed
            // const password = document.getElementById("password");
            // const passwordError = document.getElementById("passwordError");
            // if (password.value.trim() === "") {
            //     passwordError.style.display = "block";
            //     isValid = false;
            // } else {
            //     passwordError.style.display = "none";
            // }

            // If all fields are valid, submit the form
            if (isValid) {
                const formData = new FormData(form1E1);
                const data = Object.fromEntries(formData);
                console.log(data);
                fetch("http://localhost:8080/doctor/register", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(data)
                })
                .then(response => {
                    if (response.headers.get("content-type")?.includes("application/json")) {
                        return response.json();
                    } else {
                        return response.text();
                    }
                })
                .then(data => {
                    console.log("Success:", data);
                    alert("Registration successful!");
                    form1E1.reset();
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("Registration failed. Please try again.");
                });
            }
        });
=======
// Populate specialization dropdown on page load
fetch("http://localhost:8080/doctor/specialization_names")
    .then(response => response.json())
    .then(data => {
        const specializationDropdown = document.getElementById("specialization");
        data.forEach(specialization => {
            const option = document.createElement("option");
            option.value = specialization;
            option.textContent = specialization;
            specializationDropdown.appendChild(option);
        });
    })
    .catch(error => console.error("Error fetching specializations:", error));

// (Do the same for qualification if needed)

const form1E1 = document.querySelector("#registrationForm");
form1E1.addEventListener("submit", event => {
    event.preventDefault(); // Prevent form submission

    // Validate form fields
    let isValid = true;

    // Validate Doctorname
    const doctorname = document.getElementById("doctorname");
    const doctornameError = document.getElementById("doctornameError");
    if (doctorname.value.trim() === "") {
        doctornameError.style.display = "block";
        isValid = false;
    } else {
        doctornameError.style.display = "none";
    }

    // Validate email
    const email = document.getElementById("email");
    const emailError = document.getElementById("emailError");
    if (email.value.trim() === "") {
        emailError.style.display = "block";
        isValid = false;
    } else {
        emailError.style.display = "none";
    }

    // Validate qualification
    // const qualification = document.getElementById("qualification");
    // const qualificationError = document.getElementById("qualificationError");
    // if (qualification.value.trim() === "") {
    //     qualificationError.style.display = "block";
    //     isValid = false;
    // } else {
    //     qualificationError.style.display = "none";
    // }

    // Validate specialization
    const specialization = document.getElementById("specialization");
    const specializationError = document.getElementById("specializationError");
    const selectedSpecializations = Array.from(specialization.selectedOptions).map(opt => opt.value)
        .filter(val => val !== "");

    if (selectedSpecializations.length === 0) {
        specializationError.style.display = "block";
        isValid = false;
    } else {
        specializationError.style.display = "none";
    }

    // Uncomment and validate password if needed
    const password = document.getElementById("password");
    const passwordError = document.getElementById("passwordError");
    if (password.value.trim() === "") {
        passwordError.style.display = "block";
        isValid = false;
    } else {
        passwordError.style.display = "none";
    }

    // If all fields are valid, submit the form
    if (isValid) {
        const formData = new FormData(form1E1);
        const data = Object.fromEntries(formData);
        data.specialization = selectedSpecializations; // override with array

        console.log("Form Data:", data);
        fetch("http://localhost:8080/doctor/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.headers.get("content-type")?.includes("application/json")) {
                    return response.json();
                } else {
                    return response.text();
                }
            })
            .then(data => {
                console.log("Success:", data);
                alert("Registration successful!");
                form1E1.reset();
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Registration failed. Please try again.");
            });
    }
});
>>>>>>> 955846c05ffbfabe13a32ac8b006f2aee3e71d24
