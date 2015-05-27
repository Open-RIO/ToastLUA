
files = ['gradlew', 'build.gradle']

files.each do |filename|
  file_content = File.read(filename)
  new_content = file_content.gsub(/\r\n?/, "\n")

  File.open(filename, "w") { |file| file.puts new_content }
end
