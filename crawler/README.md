# Bundle 
dnf -y install gcc ruby-devel rubygems libffi-devel rpm-build zlib-devel libpqxx
dnf -y install -devel ImageMagick-devel ImageMagick  bzip2-devel --allowerasing

# Phantomjs
dnf install libXext  libXrender  fontconfig  libfontconfig.so.1

# Linux tools
dnf install parallel poppler-utils

# Install
gem install bundler 
bundle install


