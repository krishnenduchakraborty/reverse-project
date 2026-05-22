import tkinter as tk
from tkinter import messagebox

def on_click():
    messagebox.showinfo("বলো ভাই", "সাবাস! তোমার প্রথম GUI অ্যাপ কাজ করছে!")

root = tk.Tk()
root.title("My First Termux App")
root.geometry("300x200")

label = tk.Label(root, text="স্বাগতম টার্মাক্স দুনিয়ায়!", pady=20)
label.pack()

button = tk.Button(root, text="এখানে টিপ দাও", command=on_click)
button.pack()

root.mainloop()
