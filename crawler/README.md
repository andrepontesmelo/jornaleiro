# Bundle 
dnf -y install gcc mysql-devel ruby-devel rubygems libffi-devel rpm-build zlib-devel  

# Phantomjs
dnf install libXext  libXrender  fontconfig  libfontconfig.so.1

# Scripts
dnf install parallel poppler-utils

# Install
gem install bundler 
bundle install


