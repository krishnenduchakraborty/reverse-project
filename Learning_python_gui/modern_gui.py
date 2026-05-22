import customtkinter as ctk
ctk.DrawEngine.preferred_drawing_method = "polygon_shapes"
# Appearance and Theme
ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")

def on_click():
    user_name = entry.get()
    if user_name:
        label.configure(text=f"Welcome, {user_name}!", text_color="#1fcf8d")
    else:
        label.configure(text="Please enter a name!", text_color="#ff5555")

# Main Window
root = ctk.CTk()
root.title("Termux Modding Tool v1.0")
root.geometry("400x350")

# Heading Label
label = ctk.CTkLabel(root, text="Enter your Name below:", font=("Arial", 18, "bold"))
label.pack(pady=30)

# Input Box
entry = ctk.CTkEntry(root, placeholder_text="Type here...", width=250)
entry.pack(pady=10)

# Modern Button
button = ctk.CTkButton(root, text="Confirm", command=on_click, corner_radius=10)
button.pack(pady=30)

# Footer
footer = ctk.CTkLabel(root, text="Developed in Termux XFCE", font=("Arial", 10))
footer.pack(side="bottom", pady=10)

root.mainloop()
