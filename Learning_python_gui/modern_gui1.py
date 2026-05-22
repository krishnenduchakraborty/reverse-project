import customtkinter as ctk
from tkinter import filedialog
import warnings

# Hiding those annoying font warnings
warnings.filterwarnings("ignore")

ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")

selected_file = ""

def browse_file():
    global selected_file
    file_path = filedialog.askopenfilename(
        title="Select APK File",
        filetypes=[("APK files", "*.apk"), ("All files", "*.*")]
    )
    if file_path:
        selected_file = file_path
        # Show only the filename on the label
        file_name = file_path.split('/')[-1]
        status_label.configure(text=f"Target: {file_name}", text_color="#1fcf8d")
        print(f"File selected: {file_path}")

def start_decompile():
    if selected_file:
        status_label.configure(text="Decompiling... Please wait.", text_color="#f1c40f")
        # Logic for APKTool will go here
        print(f"Running: apktool d {selected_file}")
    else:
        status_label.configure(text="Error: Select an APK first!", text_color="#ff5555")

# UI Setup
root = ctk.CTk()
root.title("Termux APK Modder Pro")
root.geometry("500x400")

# Title
title_label = ctk.CTkLabel(root, text="APK Reverse Engineering Tool", font=("Arial", 20, "bold"))
title_label.pack(pady=20)

# Status Display
status_label = ctk.CTkLabel(root, text="No file selected", font=("Arial", 14))
status_label.pack(pady=10)

# Browse Button
browse_btn = ctk.CTkButton(root, text="BROWSE APK", command=browse_file, fg_color="#34495e", hover_color="#2c3e50")
browse_btn.pack(pady=15)

# Decompile Button
decompile_btn = ctk.CTkButton(root, text="START DECOMPILE", command=start_decompile, fg_color="#27ae60", hover_color="#219150")
decompile_btn.pack(pady=15)

# Small Credit
footer = ctk.CTkLabel(root, text="Running on Termux XFCE4", font=("Arial", 10))
footer.pack(side="bottom", pady=10)

root.mainloop()
