class CapybaraUtil
  def set_driver(driver)
    Capybara.current_driver = driver

    if Capybara.current_driver == :poltergeist
      require 'capybara/poltergeist'
      Capybara.javascript_driver = :poltergeist
      Capybara.run_server = false|

          options = {js_errors: false}
      Capybara.register_driver :poltergeist do |app|
        Capybara::Poltergeist::Driver.new(app, options)
      end
    else
      Capybara::Selenium::Driver.class_eval do
        def quit
          puts 'Press ENTER to close browser window.'
          $stdin.gets
          @browser.quit
        rescue Errno::ECONNREFUSED
        end
      end
    end

  end
end