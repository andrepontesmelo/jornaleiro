# Capybara Poltergeist vs Selenium switch
class CapybaraUtil
  def use_poltergeist
    require 'capybara/poltergeist'
    Capybara.current_driver = Capybara.javascript_driver = :poltergeist
    Capybara.run_server = false
    options = { js_errors: false }
    Capybara.register_driver :poltergeist do |app|
      Capybara::Poltergeist::Driver.new(app, options)
    end
  end

  def use_selenium
    Capybara.current_driver = :selenium
    Capybara::Selenium::Driver.class_eval do
      def quit
        puts 'Press ENTER to close browser window.'
        $stdin.gets
        @browser.quit
      end
    end
  end
end
