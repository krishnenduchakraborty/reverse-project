document.addEventListener("DOMContentLoaded", () => {
    
    // Scroll Spy (Active Navigation Highlight)
    const sections = document.querySelectorAll("section");
    const navLinks = document.querySelectorAll("nav a");

    window.addEventListener("scroll", () => {
        let currentSectionId = "";
        
        sections.forEach(section => {
            const sectionTop = section.offsetTop;
            const sectionHeight = section.clientHeight;
            
            // Check current scroll position
            if (pageYOffset >= (sectionTop - sectionHeight / 3)) {
                currentSectionId = section.getAttribute("id");
            }
        });

        navLinks.forEach(link => {
            link.style.backgroundColor = ""; // Reset background
            link.style.color = "";           // Reset text color
            
            if (link.getAttribute("href") === `#${currentSectionId}`) {
                link.style.backgroundColor = "#2563eb"; 
                link.style.color = "#ffffff";           
            }
        });
    });

    // Table Interaction (Click Feature)
    const tableCells = document.querySelectorAll("table td");
    
    tableCells.forEach(cell => {
        cell.style.cursor = "pointer";
        
        cell.addEventListener("click", () => {
            // Log cell text to console
            console.log(`User checked status: ${cell.textContent}`);
            
            // Temporary background flash animation
            const originalBg = cell.style.backgroundColor;
            cell.style.backgroundColor = "#dbeafe";
            
            setTimeout(() => {
                cell.style.backgroundColor = originalBg;
            }, 300);
        });
    });
    
});
