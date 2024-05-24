import os
import re

# Define the directory containing the .sgf files
directory = 'game_data/'

# Define the output file
output_file = 'extracted_tokens.txt'

# Define regex patterns
dropb_pattern = re.compile(r'(P[01])\[\d+\sDropb\s(\w+)\s\w+\s\w+\s([\\/\w\d-]+)')
dropb_pattern2 = re.compile(r'(P[01])\[\d+\sDropb\s(\w+)\s\w+\s\w+\s(\.)')
move_pattern = re.compile(r'(P[01])\[\d+\sMove\s\w+\s(\w+)\s\w+\s\d+\s([\\/\w\d-]+)')
move_pattern2 = re.compile(r'(P[01])\[\d+\sMove\s\w+\s(\w+)\s\w+\s\d+\s(\.)')
re_pattern = re.compile(r'RE\[\w+\s(.+?)\s(\w+)\]')
player_pattern = re.compile(r'(P[01])\[id\s"(.+?)"\]')

# Initialize a dictionary to hold the results for each file
results = {}

# Process each .sgf file in the directory
for filename in os.listdir(directory):
    if filename.endswith('.sgf'):
        if ('guest' in filename):
            print("")
        else:
            filepath = os.path.join(directory, filename)
            with open(filepath, 'r') as file:
                data = file.read()
            
            # Split the data into lines
            lines = data.strip().split('\n')
            
            # Initialize lists to hold the results for the current file
            tokens = []
            winning_player = ""
            player_info = ""
            
            # Iterate over each line and apply the regex
            for line in lines:
                if 'Dropb' in line:
                    match = dropb_pattern.search(line)
                    if match:
                        tokens.append((match.group(1), match.group(2), match.group(3)))
                    match = dropb_pattern2.search(line)
                    if match:
                        tokens.append((match.group(1), match.group(2), match.group(3)))
                elif 'Move' in line:
                    match = move_pattern.search(line)
                    if match:
                        tokens.append((match.group(1), match.group(2), match.group(3)))
                    match = move_pattern2.search(line)
                    if match:
                        tokens.append((match.group(1), match.group(2), match.group(3)))
                elif 'RE' in line:
                    match = re_pattern.search(line)
                    if match:
                        winning_player = match.group(2)
                elif 'id' in line:
                    match = player_pattern.search(line)
                    if match:
                        if winning_player in line:
                            player_info = match.group(1)
            
            # Store the results in the dictionary
            results[filename] = {
                'moves': tokens,
                'winning_player': winning_player,
                'players': player_info
            }

# Write the results to the output file
with open(output_file, 'w') as f:
    for filename, tokens in results.items():
        f.write(f"File: {filename}\n")
        f.write(f"Winning Player: {tokens['players']}\n")
        # Printing moves with commas and semicolons
        move_strings = []
        for token_triplet in tokens['moves']:
            move_strings.append(f"{token_triplet[0]}, {token_triplet[1]}, {token_triplet[2]}")
        f.write(";".join(move_strings) + "\n")
        
        f.write("\n")

print(f"Tokens extracted and saved to {output_file}")
